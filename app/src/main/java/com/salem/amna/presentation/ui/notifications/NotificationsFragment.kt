package com.salem.amna.presentation.ui.earnings

import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.databinding.FragmentEarningsBinding
import com.salem.amna.databinding.FragmentNotificationsBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.notifications.NotificationsState
import com.salem.amna.presentation.ui.notifications.NotificationsViewModel
import com.salem.amna.presentation.ui.notifications.adapters.new_adapter.NewNotificationsAdapter
import com.salem.amna.presentation.ui.notifications.adapters.old_adapter.OldNotificationsAdapter
import com.salem.amna.util.hideView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BaseFragment() {

    private val binding: FragmentNotificationsBinding by lazy {
        FragmentNotificationsBinding.inflate(layoutInflater)
    }

    private val mViewModel: NotificationsViewModel by viewModels()

    private lateinit var newAdapter: NewNotificationsAdapter
    private lateinit var oldAdapter: OldNotificationsAdapter

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
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener{
            baseActivity.onBackPressed()
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            mViewModel.uiState.collect { state ->
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

    private fun initData(state: NotificationsState) {
        newAdapter = NewNotificationsAdapter()
        if (state.result?.unreadNotifications.isNullOrEmpty())
            return
        else
            newAdapter.setList(state.result?.unreadNotifications!!)
        binding.rvNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newAdapter
        }

        oldAdapter = OldNotificationsAdapter()
        if (state.result?.readNotifications.isNullOrEmpty())
            return
        else
            oldAdapter.setList(state.result?.readNotifications!!)
        binding.rvOldNotifications.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newAdapter
        }
    }

    override fun navigate() {
        lifecycleScope.launchWhenStarted {
            mViewModel.navigation.collect { navigation ->
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
            mViewModel.effect.collect { effect ->
                when (effect) {
                    is UiEffect.ShowToast -> {
//                        Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }

            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = NotificationsFragment().apply {
            arguments = Bundle().apply {
            }
        }
    }
}