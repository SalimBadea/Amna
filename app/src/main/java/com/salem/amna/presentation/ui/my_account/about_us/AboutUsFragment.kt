package com.salem.amna.presentation.ui.my_account.about_us

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentAboutUsBinding
import com.salem.amna.databinding.FragmentMyAccountBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.hideView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutUsFragment : BaseFragment() {

    private val binding: FragmentAboutUsBinding by lazy {
        FragmentAboutUsBinding.inflate(layoutInflater)
    }

    private val viewModel: PagesViewModel by viewModels()

    private var facebook: String = ""
    private var twitter: String = ""
    private var instagram: String = ""
    private var telegram: String = ""
    private var whatsapp: String = ""

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.hideView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.hideView()
        return binding.root
    }

    override fun initVar() {
        viewModel.onEvent(PagesEvent.GetAbout)
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.ivFacebook.setOnClickListener {
            if (!facebook.startsWith("http://") || !facebook.startsWith("https://"))
                facebook = "http://$facebook"
            openLink(facebook)
        }
        binding.ivWhatsApp.setOnClickListener {
            if (!whatsapp.startsWith("http://") || !whatsapp.startsWith("https://"))
                whatsapp = "http://$whatsapp"
            openLink(whatsapp)
        }
        binding.ivTelegram.setOnClickListener {
            if (!telegram.startsWith("http://") || !telegram.startsWith("https://"))
                telegram = "http://$telegram"
            openLink(telegram)
        }
        binding.ivInstagram.setOnClickListener {
            if (!instagram.startsWith("http://") || !instagram.startsWith("https://"))
                instagram = "http://$instagram"
            openLink(instagram)
        }
        binding.ivTwitter.setOnClickListener {
            if (!twitter.startsWith("http://") || !twitter.startsWith("https://"))
                twitter = "http://$twitter"
            openLink(twitter)
        }

    }

    private fun openLink(link: String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        requireContext().startActivity(browserIntent)
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
                    initData(state)
                    hideLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initData(state: PagesState) {
        if (state.result?.about != null) {
            binding.tvContent.text = fromHtml(state.result.about.content)

            facebook = state.result.about.facebook
            instagram = state.result.about.instagram
            twitter = state.result.about.twitter
            telegram = state.result.about.telegram
            whatsapp = state.result.about.whats
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
//                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    fun fromHtml(str: String): Spanned? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) Html.fromHtml(
            str,
            Html.FROM_HTML_MODE_LEGACY
        ) else Html.fromHtml(str)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AboutUsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}