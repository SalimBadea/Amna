package com.salem.amna.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentLoginBinding
import com.salem.amna.presentation.MainActivity
import com.salem.amna.presentation.ui.auth.forget_password.ForgetPasswordFragment
import com.salem.amna.presentation.ui.auth.register.RegisterFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.loginBtn.setOnClickListener {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finishAffinity()
        }

        binding.forgetPasswordTv.setOnClickListener {
            replaceFragment(ForgetPasswordFragment(), R.id.fragmentContainerView, true)
        }

        binding.registerNowTv.setOnClickListener {
            replaceFragment(RegisterFragment(), R.id.fragmentContainerView, true)
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
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}