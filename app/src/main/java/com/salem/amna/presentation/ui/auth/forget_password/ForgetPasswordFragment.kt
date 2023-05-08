package com.salem.amna.presentation.ui.auth.forget_password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentForgetPasswordBinding
import com.salem.amna.presentation.ui.auth.verification_code.VerificationCodeFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgetPasswordFragment : BaseFragment() {

    private val binding: FragmentForgetPasswordBinding by lazy {
        FragmentForgetPasswordBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.sendBtn.setOnClickListener {
            replaceFragment(VerificationCodeFragment(), R.id.fragmentContainerView, true)
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
        fun newInstance() = ForgetPasswordFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}