package com.salem.amna.presentation.ui.auth.account_type

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.FragmentAccountTypeBinding
import com.salem.amna.presentation.ui.auth.language.LanguageFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class AccountTypeFragment : BaseFragment() {

    private val binding: FragmentAccountTypeBinding by lazy {
        FragmentAccountTypeBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var localePreference: LocalePreference

    private var type: Int = 1

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
        selectHome()
    }

    override fun onEvent() {

        binding.continueBtn.setOnClickListener {
            lifecycleScope.launchWhenResumed {
                localePreference.setAccountType(type)
            }
            replaceFragment(LanguageFragment(), R.id.fragmentContainerView, true)
        }

        binding.homeLayout.setOnClickListener {
            selectHome()
        }

        binding.workLayout.setOnClickListener {
            selectWork()
        }
    }

    private fun selectHome() {
        type = 1

        binding.homeLayout.setBackgroundResource(R.drawable.bg_selected_lang_type)
        binding.homeIcon.compoundDrawables.getOrNull(R.drawable.ic_home)
        setTextViewDrawableTintColor(binding.homeIcon, R.color.white)
        binding.homeIcon.setBackgroundResource(R.drawable.bg_selected_category)

        binding.workLayout.setBackgroundResource(R.drawable.bg_white)
        binding.workIcon.compoundDrawables.getOrNull(R.drawable.ic_work)
        setTextViewDrawableTintColor(binding.workIcon, R.color.darkGray)
        binding.workIcon.setBackgroundResource(R.drawable.bg_unselected_category)
    }

    private fun selectWork() {
        type = 2

        binding.workLayout.setBackgroundResource(R.drawable.bg_selected_lang_type)
        binding.workIcon.compoundDrawables.getOrNull(R.drawable.ic_work)
        setTextViewDrawableTintColor(binding.workIcon, R.color.white)
        binding.workIcon.setBackgroundResource(R.drawable.bg_selected_category)

        binding.homeLayout.setBackgroundResource(R.drawable.bg_white)
        binding.homeIcon.compoundDrawables.getOrNull(R.drawable.ic_home)
        setTextViewDrawableTintColor(binding.homeIcon, R.color.darkGray)
        binding.homeIcon.setBackgroundResource(R.drawable.bg_unselected_category)
    }

    fun setTextViewDrawableTintColor(textView: TextView, color: Int) {
        for (drawable in textView.compoundDrawables) {
            if (drawable != null) {
                drawable.colorFilter =
                    PorterDuffColorFilter(
                        ContextCompat.getColor(textView.context, color),
                        PorterDuff.Mode.SRC_IN
                    )
            }
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