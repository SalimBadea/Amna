package com.salem.amna.presentation.ui.add_product

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseActivity
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.data.models.response.add_product.Brands
import com.salem.amna.data.models.response.add_product.Status
import com.salem.amna.databinding.DialogAddItemCartBinding
import com.salem.amna.databinding.FragmentAddProductBinding
import com.salem.amna.presentation.MainActivity
import com.salem.amna.presentation.common.AppSharedViewModel
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.add_product.adapter.ItemImagesAdapter
import com.salem.amna.presentation.ui.cart.CartEvent
import com.salem.amna.presentation.ui.cart.CartFragment
import com.salem.amna.presentation.ui.cart.CartViewModel
import com.salem.amna.presentation.ui.common.ImageDialogFragment
import com.salem.amna.presentation.ui.home.HomeFragment
import com.salem.amna.util.*
import com.salem.amna.util.helpers.RealPathUtil.getImageUri
import com.salem.amna.util.helpers.StaticMethods.getRealPathFromURI
import com.salem.amna.util.helpers.Logging
import com.salem.amna.util.helpers.PhotoUploadManager
import com.salem.amna.util.helpers.RealPathUtil
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import javax.inject.Inject

@AndroidEntryPoint
class AddProductFragment : BaseFragment(), ItemImagesAdapter.OnClick {

    private val TAG = "AddProductFragment"
    private val binding: FragmentAddProductBinding by lazy {
        FragmentAddProductBinding.inflate(layoutInflater)
    }

    private var brandId: Int = 0
    private var statusId: Int = 0

    private lateinit var dialog: Dialog

     private val appSharedViewModel: AppSharedViewModel by activityViewModels()

    private val dialogAddToCartBinding by lazy {
        DialogAddItemCartBinding.inflate(layoutInflater)
    }

    private var itemImages: MutableList<MultipartBody.Part> = mutableListOf()

    private val imagesAdapter by lazy {
        ItemImagesAdapter()
    }

    private val viewModel: AddProductViewModel by viewModels()

    private val cartViewModel: CartViewModel by viewModels()

    private lateinit var product: CategoryItemModel

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    override fun initVar() {
        dialog = Dialog(requireContext())

        lifecycleScope.launchWhenStarted {
            appSharedViewModel.product.collect{
                product = it!!
                viewModel.getBrands(it.id)
                Logging.log("$product")
            }
        }

        viewModel.getStatuses()

        setupImagesRV()
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.emptyImageView.setOnClickListener {
            ImageDialogFragment.newInstance("product").show(childFragmentManager, "Dialog")
        }

        binding.saveBtn.setOnClickListener {
            if (brandId != 0) {
                if (statusId != 0) {
                    viewModel.onEvent(
                        AddProductEvent.AddItem(
                            product.id,
                            1,
                            itemImages,
                            brandId,
                            binding.descEt.text.toString(),
                            statusId
                        )
                    )
                }else{
                    showToast("Please select product status")
                }
            }else{
                showToast("Please select product brand")
            }
        }

    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
             if (state.isAdded) {
                    showSuccessDialog(state)
                    hideLoadingDialog()
                } else if (state.isSuccessful) {
                    initData()
                    initBrands(state.resultBrands?.brands, state.brandId)
                    initStatuses(state.resultStatuses?.items_statuses, state.statusId)
                    hideLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun showSuccessDialog(state: AddProductState) {
        dialog.setContentView(dialogAddToCartBinding.root)
        val window = dialog.window
        window?.setLayout(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        val margin = resources.getDimensionPixelSize(com.intuit.sdp.R.dimen._16sdp)
        val inset = InsetDrawable(ColorDrawable(Color.TRANSPARENT), margin)
        window?.setBackgroundDrawable(inset)
        dialog.setCancelable(false)

        dialogAddToCartBinding.closeIcon.setOnClickListener {
            dialog.dismiss()
            baseActivity.onBackPressed()
        }
        dialogAddToCartBinding.cart.setOnClickListener {
            requireContext().startActivity(Intent(requireContext(), MainActivity::class.java).putExtra("position", 5))
            dialog.dismiss()
        }

        dialogAddToCartBinding.addNew.setOnClickListener {
            dialog.dismiss()
            baseActivity.onBackPressed()
        }
        dialog.show()
    }

    private fun initBrands(list: List<Brands?>?, selection: Int?) {
        val areas = mutableListOf<String>()
        if (list != null) {
            var selectedBrand = -1
            for (type in list) {
                areas.add(type?.name ?: "")
                selection?.let {
                    if (it != 0 && type?.id == selection) {
                        selectedBrand = type.index ?: -1
                    }
                }
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_area_type, areas)
            binding.brandField.setAdapter(adapter)
            if (selectedBrand != -1 ) {
                val select = list[selectedBrand]
                select?.let {
                    viewModel.onEvent(AddProductEvent.BrandChanged(select.id?:0))
                    binding.brandField.setText(select.name, false)
                    brandId = select.id
                }
            }

            binding.brandField.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d(TAG, "intiCitiesSpinner: $position")
                    viewModel.onEvent(AddProductEvent.BrandChanged(list[position]?.id?:0))
                    brandId = list[position]?.id!!
                }
        }
    }

    private fun initStatuses(list: List<Status?>?, selection: Int?) {
        val ststuses = mutableListOf<String>()
        if (list != null) {
            var selectedStatus = -1
            for (type in list) {
                ststuses.add(type?.name ?: "")
                selection?.let {
                    if (it != 0 && type?.id == selection) {
                        selectedStatus = type.index ?: -1
                    }
                }
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_area_type, ststuses)
            binding.statusField.setAdapter(adapter)
            if (selectedStatus != -1 ) {
                val select = list[selectedStatus]
                select?.let {
                    viewModel.onEvent(AddProductEvent.StatusesChanged(select.id?:0))
                    binding.statusField.setText(select.name, false)
                    statusId = select.id
                }
            }
            binding.statusField.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d(TAG, "intiCitiesSpinner: $position")
                    viewModel.onEvent(AddProductEvent.StatusesChanged(list[position]?.id ?: 0))
                    statusId = list[position]?.id!!

                }
        }
    }

    private fun initData() {
        binding.productLayout.tvProduct.text = product.name
        binding.productLayout.tvCategory.text = product.category?.name
        binding.productLayout.tvContent.text =
            "${product.points} ${getString(R.string.points_worth)}"
        binding.productLayout.ivProduct.loadImageFromInternet(
            product.image,
            ContextCompat.getDrawable(requireContext(), R.drawable.logo)
        )
    }

    private fun setupImagesRV() {
        context?.let {
            imagesAdapter.setListener(this)
            binding.rvImages.layoutManager =
                LinearLayoutManager(it, LinearLayoutManager.HORIZONTAL, true)
            binding.rvImages.adapter = imagesAdapter
        }
    }

    override fun navigate() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigation.collect { navigation ->
                when (navigation) {
                    NavigationCommand.Back -> {
                        baseActivity.onBackPressed()
                    }
                    is NavigationCommand.ToDirection -> {
                        findNavController().navigate(navigation.directions)
                    }
                    else -> {}
                }

            }
        }
    }

    override fun showEffect() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is UiEffect.ShowToast -> {
//                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
    }

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.hideView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.hideView()
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            Log.d(TAG, "onActivityResult: $requestCode")
            var selectedImageUri: Uri? = null
            var pathBefore: String? = null
            var itemImage: MultipartBody.Part? = null
            if (requestCode == ImageDialogFragment.IMAGE_PRODUCT_REQUEST_CODE) {
                selectedImageUri = data?.data
                Logging.log(selectedImageUri.toString())
                if (selectedImageUri != null) {
                    pathBefore = RealPathUtil.getRealPath(requireActivity(), selectedImageUri)
                    itemImage = PhotoUploadManager.uploadItemImages(pathBefore)
                    itemImages.add(itemImage)
                    imagesAdapter.addItem(selectedImageUri)
                    Logging.log("MultiPartGallery >> ${itemImages.toString()}")
                    Logging.log("MultiPartGallery >> ${itemImages.size}")
//                    binding?.rvBeforeImages?.scrollToPosition(beforeAdapter.itemCount - 1)
                    showToast(getString(R.string.image_attched))
                } else {
                    Logging.log("an error occurred")
                }

            } else if (requestCode == ImageDialogFragment.REQUEST_PRODUCT_IMAGE_CAPTURE) {
                //CAMERA
                val photo = data?.extras?.get("data") as Bitmap
                selectedImageUri = getImageUri(photo, requireContext())
                pathBefore = getRealPathFromURI(requireActivity(), selectedImageUri)
                itemImage = PhotoUploadManager.uploadItemImages(pathBefore)
                itemImages.add(itemImage)
                imagesAdapter.addItem(selectedImageUri)
                Log.e(TAG, "onActivityResult: Path >>" + pathBefore)
                Logging.log("MultiPartCamera >> ${itemImages.toString()}")
                Logging.log("MultiPartCamera >> ${itemImages.size}")
//                binding.rvImages.scrollToPosition(imagesAdapter.itemCount - 1)
//                showToast(getString(R.string.image_attched))

                Toast.makeText(requireContext(), "تم حفظ الصورة !", Toast.LENGTH_SHORT).show()
//                uploadImageViewModel.handleImageFromCamera("before", data)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(product: CategoryItemModel) = AddProductFragment().apply {
            arguments = Bundle().apply {
                putParcelable("PRODUCT", product)

            }
        }
    }

    override fun remove(position: Int) {
        imagesAdapter.removeItem(position)
        imagesAdapter.notifyItemRemoved(position)
        itemImages.removeAt(position)
    }
}