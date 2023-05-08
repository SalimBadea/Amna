package com.salem.amna.presentation.ui.my_account.privacy_policy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentMyAccountBinding
import com.salem.amna.databinding.FragmentPrivacyPolicyBinding

class PrivacyPolicyFragment : BaseFragment() {

    private val binding: FragmentPrivacyPolicyBinding by lazy {
        FragmentPrivacyPolicyBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View = binding.root

    override fun initVar() {
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
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
        fun newInstance() =
            PrivacyPolicyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}