package com.salem.amna.presentation.ui.cart

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.databinding.FragmentCartBinding
import com.salem.amna.presentation.MainActivity
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.cart.adapter.CartItemAdapter
import com.salem.amna.util.hideView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment() {

    private val binding: FragmentCartBinding by lazy {
        FragmentCartBinding.inflate(layoutInflater)
    }

    private val viewModel: CartViewModel by viewModels()

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

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
            baseActivity.onBackPressed()
        }

        binding.confirmBtn.setOnClickListener {
            viewModel.onEvent(CartEvent.Checkout(1, 1))
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
                    initData(state)
                    hideLoadingDialog()
                } else if (state.isConfirmed) {
                    requireActivity().finishAffinity()
                    requireContext().startActivity(
                        Intent(
                            requireContext(),
                            MainActivity::class.java
                        ).putExtra("position", 0)
                    )
                    showLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initData(state: CartState) {
        binding.tvCount.text = state.result?.statistics?.items_count.toString()
        binding.tvPoints.text = state.result?.statistics?.total_points.toString()

        initRecyclerView(state.result?.items!!)
    }

    private fun initRecyclerView(list: MutableList<CategoryItemModel>) {
        val adapter = CartItemAdapter()

        adapter.list = list

        binding.listSize = adapter.itemCount

        binding.rvCart.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCart.adapter = adapter

        adapter.setOnDeleteClickedListener {
            viewModel.onEvent(CartEvent.DeleteItem(it.id))
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

    companion object {

        @JvmStatic
        fun newInstance() =
            CartFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}