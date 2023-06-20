package com.salem.amna.presentation.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentHomeBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.cart.CartFragment
import com.salem.amna.presentation.ui.earnings.NotificationsFragment
import com.salem.amna.presentation.ui.home.adapter.ItemSliderAdapter
import com.salem.amna.util.hideView
import com.salem.amna.util.replaceFragment
import com.salem.amna.util.showView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class   HomeFragment : BaseFragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    private val viewModel:HomeViewModel by viewModels()

    private val sliderAdapter by lazy {
        ItemSliderAdapter(requireContext())
    }

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.showView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.showView()
        return binding.root
    }

    override fun initVar() {

        viewModel.getHome(null)

        binding.toolbar.cartImage.setOnClickListener {
            replaceFragment(CartFragment(), R.id.fragmentContainer, true)
        }
    }

    override fun onEvent() {
        binding.toolbar.notificationsImage.setOnClickListener{
            replaceFragment(NotificationsFragment(), R.id.fragmentContainer, true)
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
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

    private fun initData(state: HomeState) {
        val slides = state.result?.slides
        sliderAdapter.addList(slides!!)

        binding.sliderHome.setSliderAdapter(sliderAdapter)
        binding.sliderHome.setIndicatorAnimation(IndicatorAnimationType.WORM) //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        binding.sliderHome.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.sliderHome.autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH
        binding.sliderHome.indicatorSelectedColor = Color.WHITE
        binding.sliderHome.indicatorUnselectedColor = Color.GRAY
        binding.sliderHome.scrollTimeInSec = 4 //set scroll delay in seconds :
        binding.sliderHome.startAutoCycle()

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
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}