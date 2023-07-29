package com.salem.amna.presentation.ui.home

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentHomeBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.cart.CartFragment
import com.salem.amna.presentation.ui.earnings.NotificationsFragment
import com.salem.amna.presentation.ui.home.adapter.ItemOrderAdapter
import com.salem.amna.presentation.ui.home.adapter.ItemSliderAdapter
import com.salem.amna.util.hideView
import com.salem.amna.util.replaceFragment
import com.salem.amna.util.showView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_new_address_bottom_sheet.*

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

    private val ordersAdapter by lazy {
        ItemOrderAdapter(requireContext())
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

        ordersAdapter.addList(state.result.leftovers)
        binding.rvOrders.layoutManager = LinearLayoutManager(requireContext())
        binding.rvOrders.adapter = ordersAdapter

        if (state.result.last_leftovers.isNotEmpty()) {
            binding.lastOrder.tvOrderNumber.text = "${state.result.last_leftovers[0].number}"
            binding.lastOrder.tvOrderDate.text = "${state.result.last_leftovers[0].date}"
//        binding.lastOrder.tvOrderStatus.text = "${state.result.last_leftovers[0].status}"
            when (state.result.last_leftovers[0].status) {
                1 -> {
                    binding.lastOrder.tvOrderStatus.text = "${getString(R.string.pending)}"
                    binding.lastOrder.tvOrderStatus.setTextColor(
                        getColor(
                            requireContext(),
                            R.color.orange
                        )
                    )
                    binding.lastOrder.tvOrderStatus.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_orange_status)
                    binding.lastOrder.ivPending.setImageResource(R.drawable.ic_radio_checked)
                    binding.lastOrder.ivAccepted.setImageResource(R.drawable.ic_radio_semi_checked)
                    binding.lastOrder.ivDelivery.setImageResource(R.drawable.ic_radio_unchecked)
                    binding.lastOrder.ivCoupon.setImageResource(R.drawable.ic_radio_unchecked)
                    binding.lastOrder.pendingView.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.view_background_green
                    )
                    binding.lastOrder.acceptView.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.view_background_gray)
                    binding.lastOrder.deliveryView.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.view_background_gray)
                }
                2 -> {
                    binding.lastOrder.tvOrderStatus.text = "${getString(R.string.accepted)}"
                    binding.lastOrder.tvOrderStatus.setTextColor(
                        getColor(
                            requireContext(),
                            R.color.blue
                        )
                    )
                    binding.lastOrder.tvOrderStatus.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_blue_status)
                    binding.lastOrder.ivPending.setImageResource(R.drawable.ic_radio_checked)
                    binding.lastOrder.ivAccepted.setImageResource(R.drawable.ic_radio_checked)
                    binding.lastOrder.ivDelivery.setImageResource(R.drawable.ic_radio_semi_checked)
                    binding.lastOrder.ivCoupon.setImageResource(R.drawable.ic_radio_unchecked)
                    binding.lastOrder.pendingView.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.view_background_green
                    )
                    binding.lastOrder.acceptView.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.view_background_green
                    )
                    binding.lastOrder.deliveryView.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.view_background_gray)
                }
                3 -> {
                    binding.lastOrder.tvOrderStatus.text = "${getString(R.string.finished)}"
                    binding.lastOrder.tvOrderStatus.setTextColor(
                        getColor(
                            requireContext(),
                            R.color.mainGreen
                        )
                    )
                    binding.lastOrder.tvOrderStatus.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_green_status)
                    binding.lastOrder.ivPending.setImageResource(R.drawable.ic_radio_checked)
                    binding.lastOrder.ivAccepted.setImageResource(R.drawable.ic_radio_checked)
                    binding.lastOrder.ivDelivery.setImageResource(R.drawable.ic_radio_checked)
                    binding.lastOrder.ivCoupon.setImageResource(R.drawable.ic_radio_semi_checked)
                    binding.lastOrder.pendingView.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.view_background_green
                    )
                    binding.lastOrder.acceptView.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.view_background_green
                    )
                    binding.lastOrder.deliveryView.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.view_background_green
                    )
                }
                4 -> {
                    binding.lastOrder.tvOrderStatus.text = "${getString(R.string.rejected)}"
                    binding.lastOrder.tvOrderStatus.setTextColor(
                        getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    binding.lastOrder.tvOrderStatus.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.bg_red_status)
                    binding.lastOrder.ivPending.setImageResource(R.drawable.ic_radio_unchecked)
                    binding.lastOrder.ivAccepted.setImageResource(R.drawable.ic_radio_unchecked)
                    binding.lastOrder.ivDelivery.setImageResource(R.drawable.ic_radio_unchecked)
                    binding.lastOrder.ivCoupon.setImageResource(R.drawable.ic_radio_unchecked)
                    binding.lastOrder.pendingView.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.view_background_gray)
                    binding.lastOrder.acceptView.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.view_background_gray)
                    binding.lastOrder.deliveryView.background =
                        ContextCompat.getDrawable(requireContext(), R.drawable.view_background_gray)

                }
            }
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
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}