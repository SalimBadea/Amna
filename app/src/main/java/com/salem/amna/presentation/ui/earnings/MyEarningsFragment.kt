package com.salem.amna.presentation.ui.earnings

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentEarningsBinding

class MyEarningsFragment : BaseFragment() {

    private val binding: FragmentEarningsBinding by lazy {
        FragmentEarningsBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.tvProducts.setOnClickListener{
            changeBackground(binding.tvProducts, binding.tvVouchers)
        }

        binding.tvVouchers.setOnClickListener{
            changeBackground(binding.tvVouchers, binding.tvProducts)
        }
    }

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }

    private fun changeBackground(selected: TextView, unselected: TextView) {
        selected.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_orange)
        selected.setTextColor(resources.getColor(R.color.black))

        unselected.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_white)
        unselected.setTextColor(resources.getColor(R.color.darkGray))
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}