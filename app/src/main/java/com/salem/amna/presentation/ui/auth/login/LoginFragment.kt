package com.salem.amna.presentation.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentLoginBinding
import com.salem.amna.presentation.MainActivity
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.auth.forget_password.ForgetPasswordFragment
import com.salem.amna.presentation.ui.auth.register.RegisterFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment() {

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater)
    }

    private val viewModel: LoginViewModel by viewModels()

    override fun getRootView(): View = binding.root

    override fun initVar() {}

    override fun onEvent() {
        binding.loginBtn.setOnClickListener {
           viewModel.onEvent(LoginEvent.Submit)
        }

        binding.forgetPasswordTv.setOnClickListener {
            replaceFragment(ForgetPasswordFragment(), R.id.fragmentContainerView, true)
        }

        binding.registerNowTv.setOnClickListener {
            replaceFragment(RegisterFragment(), R.id.fragmentContainerView, true)
        }
        handelTextChange()

    }

    private fun handelTextChange() {
        binding.loginMobileEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.loginMobileTil)
            viewModel.onEvent(
                LoginEvent.PhoneChanged(
                    s.toString()
                )
            )
        }

        binding.loginPasswordEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.loginPasswordTil)
            viewModel.onEvent(
                LoginEvent.PasswordChanged(
                    s.toString()
                )
            )
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                showErrorValidation(state)
                if (state.isSuccess) {
                    hideLoadingDialog()
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finishAffinity()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun showErrorValidation(state: LoginState) {
        state.phoneError?.let { error -> binding.loginMobileEt.error = getString(error) }
        state.passwordError?.let { error -> binding.loginPasswordEt.error = getString(error) }
    }

    override fun navigate() {
        lifecycleScope.launchWhenStarted {
            viewModel.navigation.collect { navigation ->
                when (navigation) {
                    NavigationCommand.Back -> {
                        baseActivity.onBackPressed()
                    }
                    is NavigationCommand.ToDirection -> {
                        findNavController().navigate(navigation.directions)

                    }
                    else -> {}
                }

            }
        }
    }

    override fun showEffect() {
        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect { effect ->
                when (effect) {
                    is UiEffect.ShowToast -> {
                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
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