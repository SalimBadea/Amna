package com.salem.amna.presentation.ui.my_account.contact_us

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentContactUsBinding
import com.salem.amna.presentation.common.AppSharedViewModel
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.hideView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContactUsFragment : BaseFragment() {

    private val binding: FragmentContactUsBinding by lazy {
        FragmentContactUsBinding.inflate(layoutInflater)
    }

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    private val viewModel: ContactUsViewModel by viewModels()
    private val sharedViewModel: AppSharedViewModel by activityViewModels()

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

        handelTextChange()

        binding.sendBtn.setOnClickListener {
            viewModel.onEvent(ContactUsEvent.Submit)
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {

            viewModel.uiState.collect { state ->
                showErrorValidation(state)
                if (state.isSuccess) {
                    hideLoadingDialog()
                    initDataToUi(state)

                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun handelTextChange() {
        binding.etName.doOnTextChanged { s, _, _, _ ->
            viewModel.onEvent(
                ContactUsEvent.FullNameChanged(
                    s.toString()
                )
            )
            binding.etName.setSelection(s?.length ?: 0)
        }

        binding.etPhone.doOnTextChanged { s, _, _, _ ->
            viewModel.onEvent(
                ContactUsEvent.PhoneChanged(
                    s.toString()
                )
            )
            binding.etPhone.setSelection(s?.length ?: 0)
        }
        binding.etNotes.doOnTextChanged { s, _, _, _ ->
            viewModel.onEvent(
                ContactUsEvent.MessageChanged(
                    s.toString()
                )
            )
            binding.etNotes.setSelection(s?.length ?: 0)
        }
    }

    override fun navigate() {

    }

    private fun initDataToUi(state: ContactUsState) {
        binding.etName.setText(state.name)
        binding.etPhone.setText(state.phone)
        binding.etNotes.setText(state.message)
    }

    private fun showErrorValidation(state: ContactUsState) {
        state.nameError?.let { error -> binding.etName.error = getString(error) }
        state.phoneError?.let { error -> binding.etPhone.error = getString(error) }
        state.messageError?.let { error -> binding.etNotes.error = getString(error) }
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
        fun newInstance() = ContactUsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}