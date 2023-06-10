package com.salem.amna.presentation.ui.auth.language

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.databinding.FragmentLanguageBinding
import com.salem.amna.presentation.AuthActivity
import com.salem.amna.presentation.MainActivity
import com.salem.amna.presentation.ui.auth.login.LoginFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class LanguageFragment : BaseFragment() {

    private val binding: FragmentLanguageBinding by lazy {
        FragmentLanguageBinding.inflate(layoutInflater)
    }

    private var isArabic = false
    private var from = "type"

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    @Inject
    lateinit var localePreference: LocalePreference

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {

        selectArabic()

        arguments?.let {
            from = it.getString("FROM", "type")
        }

        sharedPreferences =
            context?.getSharedPreferences("AmnaSharedPreferences", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()
        val langCode = sharedPreferences.getString("LangCode", "ar")

        if (langCode == "ar") {
            selectArabic()
        } else
            selectEnglish()
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.arLayout.setOnClickListener {
            selectArabic()
        }

        binding.enLayout.setOnClickListener {
            selectEnglish()
        }

        binding.continueBtn.setOnClickListener {
            if (from == "type") {
                runBlocking {
                    localePreference.setLangState(true)
                }
                if (isArabic) {
                    editor.putString("LangCode", "ar")
                } else {
                    editor.putString("LangCode", "en")
                }
                editor.apply()
                editor.commit()

                startActivity(Intent(requireContext(), AuthActivity::class.java))
                requireActivity().finishAffinity()
            } else {
                runBlocking {
                    localePreference.setLangState(true)
                }
                if (isArabic) {
                    editor.putString("LangCode", "ar")
                } else {
                    editor.putString("LangCode", "en")
                }
                editor.apply()
                editor.commit()
                activity?.finish()
                activity?.startActivity(Intent(context, AuthActivity::class.java))
            }
        }
    }

    private fun selectArabic() {
        isArabic = true
        binding.arLayout.setBackgroundResource(R.drawable.bg_selected_lang_type)
        binding.arTv.setBackgroundResource(R.drawable.bg_selected_category)

        binding.enLayout.setBackgroundResource(R.drawable.bg_white)
        binding.enTv.setBackgroundResource(R.drawable.bg_unselected_category)
    }

    private fun selectEnglish() {
        isArabic = false
        binding.enLayout.setBackgroundResource(R.drawable.bg_selected_lang_type)
        binding.enTv.setBackgroundResource(R.drawable.bg_selected_category)

        binding.arLayout.setBackgroundResource(R.drawable.bg_white)
        binding.arTv.setBackgroundResource(R.drawable.bg_unselected_category)
    }

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }

    companion object {
        @JvmStatic
        fun newInstance(from: String) = LanguageFragment().apply {
            arguments = Bundle().apply {
                putString("FROM", from)
            }
        }
    }
}