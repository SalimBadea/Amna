package com.salem.amna.presentation.ui.auth.forget_new_password

import android.os.Bundle
import android.view.View
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentForgetNewPasswordBinding

class ForgetNewPasswordFragment : BaseFragment() {

    private val binding: FragmentForgetNewPasswordBinding by lazy {
        FragmentForgetNewPasswordBinding.inflate(layoutInflater)
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
        fun newInstance() = ForgetNewPasswordFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}