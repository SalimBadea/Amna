package com.salem.amna.presentation.ui.on_boarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentIntroBinding

class IntroFragment(
    private val lottieRaw: Int,
    private val title: String,
    private val intro: String
) : BaseFragment() {

    private val binding: FragmentIntroBinding by lazy {
        FragmentIntroBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
        binding.introLav.setImageResource(lottieRaw)
        binding.introTitleTv.text = title
        binding.introDescriptionTv.text = intro
    }

    override fun onEvent() {
    }

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }
}