package com.salem.amna.presentation.ui.my_account.addresses

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.imageview.ShapeableImageView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.databinding.FragmentAddressesBinding
import com.salem.amna.presentation.common.AppSharedViewModel
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.my_account.addresses.adapter.AddressItemsAdapter
import com.salem.amna.presentation.ui.my_account.addresses.add.AddAddressFragment
import com.salem.amna.util.hideView
import com.salem.amna.util.replaceFragment
import com.salem.amna.util.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesFragment : BaseFragment() {

    private val binding: FragmentAddressesBinding by lazy {
        FragmentAddressesBinding.inflate(layoutInflater)
    }

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    private val viewModel: AddressViewModel by viewModels()
    private val sharedViewModel: AppSharedViewModel by activityViewModels()

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.hideView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.hideView()
        return binding.root
    }

    override fun initVar() {
        viewModel.addresses()
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.tvAddAddress.setOnClickListener {
            replaceFragment(AddAddressFragment(), R.id.fragmentContainer, true)
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {

            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
                    initAddressListItems(state.result?.addresses!!)
                    hideNoData(state.result?.addresses)
                    hideLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    override fun navigate() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigation.collect { navigation ->
                when (navigation) {
                    NavigationCommand.Back -> {
                        findNavController().popBackStack()
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
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
    }


    private fun initAddressListItems(userAddress: MutableList<AddressModel?>) {
        val adapter = AddressItemsAdapter(requireContext(),userAddress)
        binding.rvAddresses.layoutManager = LinearLayoutManager(requireContext())
        binding.rvAddresses.adapter = adapter
//        userAddress?.let {
//            adapter.differ.submitList(it)
//        }
//        binding.rvAddresses.adapter = adapter

        Log.e("Addresses", "addresses List >> $userAddress")
//        adapter.setOnAddressClickListener { adddress ->
//            adddress.id?.let { id ->
//                viewModel.onEvent(AddressEvent.MakeDefaultAddress(id))
//            }
//        }

        adapter.setOnDeleteClickedListener { address ->
            viewModel.onEvent(AddressEvent.DeleteAddress(address.id))
        }

    }

    private fun hideNoData(userAddress: List<AddressModel?>?) {
        userAddress?.let {
            if (it.isEmpty())
                binding.noData.showView()
            else
                binding.noData.hideView()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddressesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}