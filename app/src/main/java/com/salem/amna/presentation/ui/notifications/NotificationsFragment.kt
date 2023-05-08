package com.salem.amna.presentation.ui.earnings

import android.os.Bundle
import android.view.View
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentEarningsBinding

class NotificationsFragment : BaseFragment() {

    private val binding: FragmentEarningsBinding by lazy {
        FragmentEarningsBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
    }

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}