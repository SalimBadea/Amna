package com.salem.amna.presentation.ui.register_course

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseBottomSheetFragment
import com.salem.amna.databinding.FragmentRegisterInBinding
import com.salem.amna.util.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterCourseFragment : BaseBottomSheetFragment() {

    private val binding: FragmentRegisterInBinding by lazy {
        FragmentRegisterInBinding.inflate(layoutInflater)
    }

    private lateinit var navBar: BottomNavigationView
    private lateinit var customBtnLayout: ConstraintLayout

    override fun getRootView(): View {
        navBar = requireActivity().findViewById(R.id.navView)
        navBar.showView()
        customBtnLayout = requireActivity().findViewById(R.id.customBtnLayout)
        customBtnLayout.showView()
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
        fun newInstance() = RegisterCourseFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}