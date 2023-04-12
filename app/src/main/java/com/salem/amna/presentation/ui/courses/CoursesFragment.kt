package com.salem.amna.presentation.ui.courses

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentCoursesBinding

class CoursesFragment : BaseFragment() {

    private val binding: FragmentCoursesBinding by lazy {
        FragmentCoursesBinding.inflate(layoutInflater)
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
    }

    override fun onEvent() {
    }

    override fun render() {
    }

    override fun navigate() {
    }

    override fun showEffect() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = CoursesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}