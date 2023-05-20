package com.salem.amna.presentation.ui.my_account.addresses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentAddressesBinding

class AddressesFragment : BaseFragment() {

    private val binding: FragmentAddressesBinding by lazy {
        FragmentAddressesBinding.inflate(layoutInflater)
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
        fun newInstance() =
            AddressesFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}