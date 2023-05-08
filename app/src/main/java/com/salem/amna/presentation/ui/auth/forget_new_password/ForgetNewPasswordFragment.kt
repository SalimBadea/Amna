package com.salem.amna.presentation.ui.auth.forget_new_password

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentForgetNewPasswordBinding
import com.salem.amna.presentation.MainActivity
import com.salem.amna.util.replaceFragment

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
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.submitBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
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
        fun newInstance() = ForgetNewPasswordFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}