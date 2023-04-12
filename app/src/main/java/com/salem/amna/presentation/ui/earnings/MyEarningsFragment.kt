package com.salem.amna.presentation.ui.earnings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyEarningsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}