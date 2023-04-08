package com.salem.amna.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.salem.amna.R
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.FragmentSplashBinding
import com.salem.amna.presentation.ui.auth.login.LoginFragment
import com.salem.amna.presentation.ui.home.HomeFragment
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
            delay(3500)
            val extras = FragmentNavigatorExtras(
                binding.imageView to "SplashTransition"
            )
            if (localePreference.getLoginState() == true) {
                activity?.finishAffinity()
                replaceFragment(HomeFragment(), R.id.fragmentContainer, true)
            } else {
                activity?.finishAffinity()
                replaceFragment(LoginFragment(), R.id.fragmentContainer, true)
            }
        }
    }
}