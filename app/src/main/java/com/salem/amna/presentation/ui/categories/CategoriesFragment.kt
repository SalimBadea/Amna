package com.salem.amna.presentation.ui.categories

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.databinding.FragmentCategoriesBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.add_product.AddProductFragment
import com.salem.amna.util.hideView
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : BaseFragment(), CategoriesAdapter.OnItemClick {

    private val binding: FragmentCategoriesBinding by lazy {
        FragmentCategoriesBinding.inflate(layoutInflater)
    }

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    private val viewModel: CategoriesViewModel by viewModels()

    private val categoriesAdapter by lazy {
        CategoriesAdapter(requireContext(), this)
    }

    private val productsAdapter by lazy {
        ProductsAdapter(requireContext())
    }

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.hideView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.hideView()
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            if (binding.tvTitle.text.toString() == getString(R.string.categories))
                baseActivity.onBackPressed()
            else {
                viewModel.onEvent(CategoriesEvent.LoadCategories)
                binding.tvTitle.text = getText(R.string.categories)
            }
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSubCategory) {
                    initItemsData(state)
                    hideLoadingDialog()
                }else if (state.isSuccess) {
                    initData(state)
                    hideLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initItemsData(state: CategoriesState) {
        binding.rvCategories.visibility = View.VISIBLE

        productsAdapter.list = state.categoryItemsResult?.items!!

        binding.listSize = productsAdapter.itemCount

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }

        productsAdapter.setOnAddClickedListener {
            replaceFragment(AddProductFragment.newInstance(it), R.id.fragmentContainer, true)
        }
    }

    private fun initData(state: CategoriesState) {

        categoriesAdapter.list = state.result?.categories!!

        binding.listSize = categoriesAdapter.itemCount

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoriesAdapter
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

    override fun orderClicked(item: CategoriesModel) {
        binding.rvCategories.visibility = View.GONE
        viewModel.onEvent(CategoriesEvent.GetCategoryItems(item.id!!))
        binding.tvTitle.text = item.name
    }

    companion object {
        @JvmStatic
        fun newInstance() = CategoriesFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }


}