package com.salem.amna.presentation.ui.courses

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.common.CoursesModel
import com.salem.amna.data.models.common.UserModel
import com.salem.amna.databinding.FragmentCoursesBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.cart.CartFragment
import com.salem.amna.presentation.ui.my_account.AccountInfoState
import com.salem.amna.presentation.ui.my_account.AccountInfoViewModel
import com.salem.amna.util.loadImageFromInternet
import com.salem.amna.util.replaceFragment
import com.salem.amna.util.showView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CoursesFragment : BaseFragment(), CoursesAdapter.OnItemClick {

    private val binding: FragmentCoursesBinding by lazy {
        FragmentCoursesBinding.inflate(layoutInflater)
    }

    private val coursesAdapter by lazy{
        CoursesAdapter(requireContext(), this)
    }

    private lateinit var mUser: UserModel

    private val mViewModel: CoursesViewModel by viewModels()
    private val viewModel: AccountInfoViewModel by viewModels()

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
        viewModel.getAccountInfo()
    }

    override fun onEvent() {
        binding.toolbar.cartImage.setOnClickListener {
            replaceFragment(CartFragment(), R.id.fragmentContainer, true)
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

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
                    initUserData(state)
                    hideLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initUserData(state: AccountInfoState) {

        binding.toolbar.profileImage.loadImageFromInternet(
            state.result?.user?.image,
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_user)
        )

        Log.d("TAG", "initUserData: $state")

        binding.toolbar.tvNameTitle.text = state.name
    }

    private fun initData(state: CoursesState) {

        coursesAdapter.list = state.result?.courses!!
        binding.rvCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = coursesAdapter
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
        fun newInstance() = CoursesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun itemShowClicked(item: CoursesModel) {

    }
}