package com.salem.amna.presentation.ui.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.NavHostFragment
import com.salem.amna.R
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.FragmentSplashBinding
import com.salem.amna.presentation.MainActivity
import com.salem.amna.presentation.ui.auth.account_type.AccountTypeFragment
import com.salem.amna.presentation.ui.auth.login.LoginFragment
import com.salem.amna.presentation.ui.home.HomeFragment
import com.salem.amna.presentation.ui.on_boarding.OnBoardingFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var localePreference: LocalePreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding
            .inflate(
                inflater,
                container,
                false
            )
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            delay(2000)
            val extras = FragmentNavigatorExtras(
                binding.imageView to "SplashTransition"
            )
//            replaceFragment(AccountTypeFragment(), R.id.fragmentContainerView, true)
            if (localePreference.getLoginState() == true) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finishAffinity()
            } else if (localePreference.getLangState() == true){
                replaceFragment(LoginFragment(), R.id.fragmentContainerView, true)
            }else{
                replaceFragment(OnBoardingFragment(), R.id.fragmentContainerView, true)

            }
        }
    }
}