package com.salem.amna.presentation.ui.my_account.my_points

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.data.models.response.points.WithdrawalsResponse
import com.salem.amna.databinding.FragmentMyPointsBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.my_account.exchange_points.ExchangePointsEvent
import com.salem.amna.presentation.ui.my_account.exchange_points.ExchangePointsFragment
import com.salem.amna.presentation.ui.my_account.exchange_points.ExchangePointsState
import com.salem.amna.presentation.ui.my_account.exchange_points.ExchangePointsViewModel
import com.salem.amna.util.replaceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPointsFragment : BaseFragment() {

    private val binding by lazy {
        FragmentMyPointsBinding.inflate(layoutInflater)
    }

    private lateinit var withdrawalsAdapter: WithdrawalsAdapter

    private var type: String? = null

    private val viewModel: ExchangePointsViewModel by viewModels()

    override fun getRootView(): View = binding.root

    override fun initVar() {
        viewModel.onEvent(ExchangePointsEvent.GetPoints)
        viewModel.onEvent(ExchangePointsEvent.GetWithdrawals(type))
    }

    override fun onEvent() {

        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.ibExchange.setOnClickListener {
            replaceFragment(ExchangePointsFragment(), R.id.fragmentContainer, true)
        }

        binding.tvAll.setOnClickListener {
            type = null
            changeBackground(binding.tvAll, binding.tvBank, binding.tvWallet, binding.tvBankCart)
            viewModel.onEvent(ExchangePointsEvent.GetWithdrawals(type))
        }

        binding.tvBank.setOnClickListener {
            type = "bank_account"
            changeBackground(binding.tvBank, binding.tvAll, binding.tvWallet, binding.tvBankCart)
            viewModel.onEvent(ExchangePointsEvent.GetWithdrawals(type))
        }

        binding.tvWallet.setOnClickListener {
            type = "wallet"
            changeBackground(binding.tvWallet, binding.tvBank, binding.tvAll, binding.tvBankCart)
            viewModel.onEvent(ExchangePointsEvent.GetWithdrawals(type))
        }

        binding.tvBankCart.setOnClickListener {
            type = "bank_card"
            changeBackground(binding.tvBankCart, binding.tvBank, binding.tvAll, binding.tvWallet)
            viewModel.onEvent(ExchangePointsEvent.GetWithdrawals(type))
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isPoints) {
                    initData(state.pointsResult!!)
                    hideLoadingDialog()
                }
                if (state.isWithdrawals) {
                    initWithdrawals(state.withdrawalsResult)
                    Log.e("MyPoints", "withdrawalsResult >> ${state.withdrawalsResult?.withdrawals}")
                    hideLoadingDialog()
                }
                if (state.isLoading) {
                    showLoadingDialog()
                }
                if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initData(state: PointsResponse) {
        binding.tvPoints.text = "${state.points.toString()}"
        binding.tvPointsText.text = "${state.points.toString()}"
        binding.tvCurrentValue.text = "${state.money.toString()} ${getString(R.string.currency)}"
    }

    private fun initWithdrawals(state: WithdrawalsResponse?){
        if (state?.withdrawals != null) {
            withdrawalsAdapter = WithdrawalsAdapter(state.withdrawals)
            binding.rvWithdrawals.layoutManager = LinearLayoutManager(requireContext())
            binding.rvWithdrawals.adapter = withdrawalsAdapter
            if (state.withdrawals.isNotEmpty()) {
                binding.rvWithdrawals.visibility = View.VISIBLE
            } else {
                binding.rvWithdrawals.visibility = View.GONE
            }
        }
    }

    private fun changeBackground(
        selected: TextView,
        unselected1: TextView,
        unselected2: TextView,
        unselected3: TextView
    ) {
        selected.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_white)
        selected.setTextColor(resources.getColor(R.color.black))

        unselected1.background =
            ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
        unselected1.setTextColor(resources.getColor(R.color.black))

        unselected2.background =
            ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
        unselected2.setTextColor(resources.getColor(R.color.black))

        unselected3.background =
            ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
        unselected3.setTextColor(resources.getColor(R.color.black))
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

    companion object {

        @JvmStatic
        fun newInstance() =
            MyPointsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}