package com.salem.amna.presentation.ui.my_account.exchange_points

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentExchangePointsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangePointsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentExchangePointsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun getRootView(): View = binding.root

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
        fun newInstance() =
            ExchangePointsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}