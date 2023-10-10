package com.salem.amna.presentation.ui.on_boarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import androidx.viewpager2.widget.ViewPager2
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.FragmentOnBoardingBinding
import com.salem.amna.presentation.ui.auth.account_type.AccountTypeFragment
import com.salem.amna.presentation.ui.auth.login.LoginFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class OnBoardingFragment : BaseFragment() {

    private val binding: FragmentOnBoardingBinding by lazy {
        FragmentOnBoardingBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var localePreference: LocalePreference

    override fun getRootView(): View {
        return binding.root
    }

    private val introPagerAdapter by lazy {
        IntroViewPagerAdapter(
            childFragmentManager,
            lifecycle,
            titlesList = listOf(
                getString(R.string.title_1),
                getString(R.string.title_2),
                getString(R.string.title_3)
            ),
            descriptionList = listOf(
                getString(R.string.description_1),
                getString(R.string.description_2),
                getString(R.string.description_3)
            )
        )
    }

    override fun initVar() {
        binding.apply {
            viewPager.apply {
                adapter = introPagerAdapter
            }
        }
    }

    fun next() {
        val currentItem = binding.viewPager.currentItem
        val isLastItem = currentItem == introPagerAdapter.itemCount - 1
        if (isLastItem) {
//            activity?.finishAffinity()
//            replaceFragment(LoginFragment(), R.id.fragmentContainer, true)
            replaceFragment(AccountTypeFragment(), R.id.fragmentContainerView, true)
        }
        binding.viewPager.currentItem = currentItem + 1
    }

    override fun onEvent() {
        binding.continueBtn.setOnClickListener { next() }

        binding.skipBtn.setOnClickListener {
//            activity?.finishAffinity()
//            replaceFragment(LoginFragment(), R.id.fragmentContainer, true)
            replaceFragment(AccountTypeFragment(), R.id.fragmentContainerView, true)
        }
    }

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }

}