package com.salem.amna.presentation.ui.my_account

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentMyAccountBinding
import com.salem.amna.presentation.AuthActivity
import com.salem.amna.presentation.common.AppSharedViewModel
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.auth.language.LanguageFragment
import com.salem.amna.presentation.ui.my_account.about_us.AboutUsFragment
import com.salem.amna.presentation.ui.my_account.change_password.ChangePasswordFragment
import com.salem.amna.presentation.ui.my_account.contact_us.ContactUsFragment
import com.salem.amna.presentation.ui.my_account.edit_profile.EditProfileFragment
import com.salem.amna.presentation.ui.my_account.privacy_policy.PrivacyPolicyFragment
import com.salem.amna.presentation.ui.my_account.volunteering.VolunteeringFragment
import com.salem.amna.util.ShareUtils.shareImage
import com.salem.amna.util.loadImageFromInternet
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountFragment : BaseFragment() {

    private val binding: FragmentMyAccountBinding by lazy {
        FragmentMyAccountBinding.inflate(layoutInflater)
    }

    private val viewModel: AccountInfoViewModel by viewModels()
    private val sharedViewModel: AppSharedViewModel by activityViewModels()

    override fun getRootView(): View = binding.root

    override fun initVar() {
    }

    override fun onEvent() {

        binding.tvEdit.setOnClickListener {
            replaceFragment(EditProfileFragment(), R.id.fragmentContainer, true)
        }

        binding.tvChangePassword.setOnClickListener {
            replaceFragment(ChangePasswordFragment(), R.id.fragmentContainer, true)
        }

        binding.tvLanguage.setOnClickListener {
            replaceFragment(LanguageFragment(), R.id.fragmentContainer, true)
        }

        binding.tvVolunteering.setOnClickListener {
            replaceFragment(VolunteeringFragment(), R.id.fragmentContainer, true)
        }

        binding.tvContactUs.setOnClickListener {
            replaceFragment(ContactUsFragment(), R.id.fragmentContainer, true)
        }

        binding.tvInvite.setOnClickListener {
            share(R.drawable.logo)
        }

        binding.tvPrivacy.setOnClickListener {
            replaceFragment(PrivacyPolicyFragment(), R.id.fragmentContainer, true)
        }

        binding.tvAbout.setOnClickListener {
            replaceFragment(AboutUsFragment(), R.id.fragmentContainer, true)
        }

        binding.tvLogout.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
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
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initUserData(state: AccountInfoState) {

        binding.profileImage.loadImageFromInternet(
            state.result?.user?.image,
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_user)
        )

        Log.d("TAG", "initUserData: $state")

        binding.tvName.text = state.name
        binding.tvEmail.text = state.email


        sharedViewModel.setUserImage(state.result?.user?.image)
        sharedViewModel.setUserName(state.result?.user?.name)

    }


    private fun share(imgUrl: Int) {
        shareImage(imgUrl, requireContext())
    }

    companion object {
        @JvmStatic
        fun newInstance() = MyAccountFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}