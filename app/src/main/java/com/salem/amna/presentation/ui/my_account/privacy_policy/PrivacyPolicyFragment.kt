package com.salem.amna.presentation.ui.my_account.privacy_policy

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.ScrollingMovementMethod
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
import com.salem.amna.databinding.FragmentMyAccountBinding
import com.salem.amna.databinding.FragmentPrivacyPolicyBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.my_account.about_us.PagesEvent
import com.salem.amna.presentation.ui.my_account.about_us.PagesState
import com.salem.amna.presentation.ui.my_account.about_us.PagesViewModel
import com.salem.amna.util.hideView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrivacyPolicyFragment : BaseFragment() {

    private val binding: FragmentPrivacyPolicyBinding by lazy {
        FragmentPrivacyPolicyBinding.inflate(layoutInflater)
    }

    private val viewModel: PagesViewModel by viewModels()

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
        viewModel.onEvent(PagesEvent.GetPrivacy)
        binding.tvPrivacy.movementMethod = ScrollingMovementMethod()
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }
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
        if (state.result?.privacy != null)
            binding.tvPrivacy.text = fromHtml(state.result.privacy.content)
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
            PrivacyPolicyFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}