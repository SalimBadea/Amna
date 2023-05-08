package com.salem.amna.presentation.ui.my_account.edit_profile

import android.os.Bundle
import android.view.View
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentEditProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : BaseFragment() {

    private val binding: FragmentEditProfileBinding by lazy {
        FragmentEditProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun getRootView(): View {
        return binding.root
    }

    override fun initVar() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }
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
        fun newInstance() = EditProfileFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}