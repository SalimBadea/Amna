package com.salem.amna.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentHomeBinding
import com.salem.amna.presentation.ui.cart.CartFragment
import com.salem.amna.presentation.ui.earnings.NotificationsFragment
import com.salem.amna.util.hideView
import com.salem.amna.util.replaceFragment
import com.salem.amna.util.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class   HomeFragment : BaseFragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.showView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.showView()
        return binding.root
    }

    override fun initVar() {

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
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}