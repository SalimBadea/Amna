package com.salem.amna.presentation.ui.my_account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentMyAccountBinding
import com.salem.amna.presentation.AuthActivity
import com.salem.amna.presentation.ui.auth.language.LanguageFragment
import com.salem.amna.presentation.ui.my_account.about_us.AboutUsFragment
import com.salem.amna.presentation.ui.my_account.change_password.ChangePasswordFragment
import com.salem.amna.presentation.ui.my_account.contact_us.ContactUsFragment
import com.salem.amna.presentation.ui.my_account.edit_profile.EditProfileFragment
import com.salem.amna.presentation.ui.my_account.privacy_policy.PrivacyPolicyFragment
import com.salem.amna.presentation.ui.my_account.volunteering.VolunteeringFragment
import com.salem.amna.util.ShareUtils.shareImage
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAccountFragment : BaseFragment() {

    private val binding: FragmentMyAccountBinding by lazy {
        FragmentMyAccountBinding.inflate(layoutInflater)
    }

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

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
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