package com.salem.amna.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentHomeBinding
import com.salem.amna.presentation.ui.earnings.NotificationsFragment
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
        binding.toolbar.notificationsImage.setOnClickListener{
            replaceFragment(NotificationsFragment(), R.id.fragmentContainer, true)
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
        fun newInstance() = HomeFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}