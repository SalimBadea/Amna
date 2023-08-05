package com.salem.amna.presentation.ui.my_account

import android.content.Intent
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
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.FragmentMyAccountBinding
import com.salem.amna.presentation.AuthActivity
import com.salem.amna.presentation.common.AppSharedViewModel
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.auth.language.LanguageFragment
import com.salem.amna.presentation.ui.my_account.about_us.AboutUsFragment
import com.salem.amna.presentation.ui.my_account.addresses.AddressesFragment
import com.salem.amna.presentation.ui.my_account.change_password.ChangePasswordFragment
import com.salem.amna.presentation.ui.my_account.contact_us.ContactUsFragment
import com.salem.amna.presentation.ui.my_account.edit_profile.EditProfileFragment
import com.salem.amna.presentation.ui.my_account.my_points.MyPointsFragment
import com.salem.amna.presentation.ui.my_account.privacy_policy.PrivacyPolicyFragment
import com.salem.amna.presentation.ui.my_account.volunteering.VolunteeringFragment
import com.salem.amna.util.ShareUtils.shareImage
import com.salem.amna.util.loadImageFromInternet
import com.salem.amna.util.replaceFragment
import com.salem.amna.util.showView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class MyAccountFragment : BaseFragment() {

    private val binding: FragmentMyAccountBinding by lazy {
        FragmentMyAccountBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var localePreference: LocalePreference

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    private val viewModel: AccountInfoViewModel by viewModels()
    private val sharedViewModel: AppSharedViewModel by activityViewModels()

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.showView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.showView()
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {

        binding.tvEdit.setOnClickListener {
            replaceFragment(EditProfileFragment(), R.id.fragmentContainer, true)
        }

        binding.tvPoints.setOnClickListener {
            replaceFragment(MyPointsFragment(), R.id.fragmentContainer, true)
        }

        binding.tvChangePassword.setOnClickListener {
            replaceFragment(ChangePasswordFragment(), R.id.fragmentContainer, true)
        }

        binding.tvAddresses.setOnClickListener {
            replaceFragment(AddressesFragment(), R.id.fragmentContainer, true)
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
            runBlocking {
                localePreference.setLoginState(false)
            }
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