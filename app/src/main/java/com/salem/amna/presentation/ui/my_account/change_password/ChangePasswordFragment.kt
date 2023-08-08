package com.salem.amna.presentation.ui.my_account.change_password

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentChangePasswordBinding
import com.salem.amna.presentation.MainActivity
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.auth.login.LoginEvent
import com.salem.amna.presentation.ui.auth.login.LoginState
import com.salem.amna.presentation.ui.auth.login.LoginViewModel
import com.salem.amna.util.hideView

class ChangePasswordFragment : BaseFragment() {

    private val binding: FragmentChangePasswordBinding by lazy {
        FragmentChangePasswordBinding.inflate(layoutInflater)
    }

    private val viewModel: ChangePasswordViewModel by viewModels()

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.hideView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.hideView()
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.submitBtn.setOnClickListener {
            viewModel.onEvent(ChangePasswordEvent.Submit)
        }

        handelTextChange()
    }


    private fun handelTextChange() {
        binding.oldPasswordEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.oldPasswordTil)
            viewModel.onEvent(
                ChangePasswordEvent.PasswordChanged(
                    s.toString()
                )
            )
        }

        binding.newPasswordEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.newPasswordTil)
            viewModel.onEvent(
                ChangePasswordEvent.NewPasswordChanged(
                    s.toString()
                )
            )
        }

        binding.confirmNewPasswordEt.doOnTextChanged { s, _, _, _ ->
            clearErrorEditText(binding.confirmNewPasswordTil)
            viewModel.onEvent(
                ChangePasswordEvent.ConfirmNewPasswordChanged(
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
                    baseActivity.onBackPressed()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun showErrorValidation(state: ChangePasswordState) {
        state.passwordError?.let { error -> binding.oldPasswordEt.error = getString(error) }
        state.newPasswordError?.let { error -> binding.newPasswordEt.error = getString(error) }
        state.confirmNewPasswordError?.let { error -> binding.confirmNewPasswordEt.error = getString(error) }
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
        fun newInstance() = ChangePasswordFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}