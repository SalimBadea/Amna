package com.salem.amna.presentation.ui.auth.register

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentRegisterBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.auth.forget_password.ForgetPasswordFragment
import com.salem.amna.presentation.ui.auth.login.LoginFragment
import com.salem.amna.presentation.ui.auth.sharedviewmodel.AuthSharedViewModel
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {

    private val binding: FragmentRegisterBinding by lazy {
        FragmentRegisterBinding.inflate(layoutInflater)
    }

    private val TAG = "RegisterFragment"
    private val viewModel: RegisterViewModel by viewModels()
    private val sharedViewModel: AuthSharedViewModel by activityViewModels()

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.registerNowTv.setOnClickListener {
            replaceFragment(LoginFragment(), R.id.fragmentContainerView, true)
        }

        binding.registerBtn.setOnClickListener {
            viewModel.onEvent(RegisterEvent.Submit)
        }

        handelTextChange()

    }

    private fun handelTextChange() {
        binding.registerNameEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.registerNameTil)
            viewModel.onEvent(
                RegisterEvent.NameChanged(
                    s.toString()
                )
            )
        }
        binding.registerEmailEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.registerEmailTil)
            viewModel.onEvent(
                RegisterEvent.EmailChanged(
                    s.toString()
                )
            )
        }
        binding.registerMobileEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.registerMobileTil)
            viewModel.onEvent(
                RegisterEvent.PhoneChanged(
                    s.toString()
                )
            )
        }
        binding.registerPasswordEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.registerPasswordTil)
            viewModel.onEvent(
                RegisterEvent.PasswordChanged(
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
                    sharedViewModel.setRegisterData(state)
                    sharedViewModel.setCode(state.result?.code)
                    replaceFragment(LoginFragment(), R.id.fragmentContainerView, true)
                    viewModel.clearSuccessState()
                }
                if (state.isLoading) {
                    showLoadingDialog()
                } else {
                    hideLoadingDialog()
                }
                if (state.error.isNotBlank()) {
                    //showToast(state.error)
                    hideLoadingDialog()
                }
            }
        }
    }


    private fun showErrorValidation(state: RegisterState) {
        state.emailError?.let { error -> binding.registerEmailEt.error = getString(error) }
        state.nameError?.let { error -> binding.registerNameEt.error = getString(error) }
        state.passwordError?.let { error -> binding.registerPasswordEt.error = getString(error) }

        state.phoneError?.let { error -> binding.registerMobileEt.error = getString(error) }

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
                        Log.d(TAG, "showEffect: ${effect.message}")
                        showToast(effect.message)
                    }
                    else -> {}
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}