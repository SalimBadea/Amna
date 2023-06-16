package com.salem.amna.presentation.ui.my_account.edit_profile

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentEditProfileBinding
import com.salem.amna.presentation.common.AppSharedViewModel
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.my_account.AccountInfoEvent
import com.salem.amna.presentation.ui.my_account.AccountInfoState
import com.salem.amna.presentation.ui.my_account.AccountInfoViewModel
import com.salem.amna.util.hideView
import com.salem.amna.util.loadImageFromInternet
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private val binding: FragmentEditProfileBinding by lazy {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    private val viewModel: AccountInfoViewModel by viewModels()
    private val sharedViewModel: AppSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.hideView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.hideView()
        return binding.root
    }

    override fun initVar() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }
    }

    override fun onEvent() {
        binding.sendBtn.setOnClickListener {
            viewModel.onEvent(AccountInfoEvent.Submit)
        }
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

    override fun render() {
        lifecycleScope.launchWhenStarted {

            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
                    initUserData(state)
                    hideLoadingDialog()
                    showErrorValidation(state)
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun showErrorValidation(state: AccountInfoState) {
        state.emailError?.let { error -> binding.etEmail.error = getString(error) }
        state.nameError?.let { error -> binding.etName.error = getString(error) }
        state.phoneError?.let { error -> binding.etPhone.error = getString(error) }
    }
    private fun initUserData(state: AccountInfoState) {
        binding.profileImage.loadImageFromInternet(
            state.result?.user?.image,
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_user)
        )
        Log.d("TAG", "initUserData: $state")
        binding.etName.setText(state.name)
        binding.etEmail.setText(state.email)
        binding.etPhone.setText(state.phone)

        sharedViewModel.setUserImage(state.result?.user?.image)
        sharedViewModel.setUserName(state.result?.user?.name)
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}