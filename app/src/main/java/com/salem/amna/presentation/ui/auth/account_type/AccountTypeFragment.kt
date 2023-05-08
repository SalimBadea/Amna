package com.salem.amna.presentation.ui.auth.account_type

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentAccountTypeBinding
import com.salem.amna.presentation.ui.auth.login.LoginFragment
import com.salem.amna.presentation.ui.home.HomeFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountTypeFragment : BaseFragment() {

    private val binding: FragmentAccountTypeBinding by lazy {
        FragmentAccountTypeBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {

        binding.continueBtn.setOnClickListener {
            replaceFragment(LoginFragment(), R.id.fragmentContainerView, true)
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
        fun newInstance() = AccountTypeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}