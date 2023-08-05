package com.salem.amna.presentation.ui.my_account.exchange_points

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.response.points.Banks
import com.salem.amna.databinding.FragmentExchangePointsBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangePointsFragment : BaseFragment() {

    private val TAG = "ExchangePointsFragment"

    private val binding by lazy {
        FragmentExchangePointsBinding.inflate(layoutInflater)
    }

    private val viewModel: ExchangePointsViewModel by viewModels()

    private var bankId = 0

    override fun getRootView(): View = binding.root

    override fun initVar() {
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.tvBankAccount.setOnClickListener {
            changeBackground(binding.tvBankAccount, binding.tvWallet, binding.tvBankCart)
        }

        binding.tvWallet.setOnClickListener {
            changeBackground(binding.tvWallet, binding.tvBankAccount, binding.tvBankCart)
        }

        binding.tvBankCart.setOnClickListener {
            changeBackground(binding.tvBankCart, binding.tvWallet, binding.tvBankAccount)
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isSuccess) {
                    initBanks(state.banksResult?.banks, state.bankId)
                    hideLoadingDialog()
                } else if (state.isLoading) {
                    showLoadingDialog()
                } else if (state.error.isNotBlank()) {
                    hideLoadingDialog()
                }
            }
        }
    }

    private fun initBanks(list: List<Banks?>?, selection: Int?) {
        val bank = mutableListOf<String>()
        if (list != null) {
            var selectedBank = -1
            for (type in list) {
                bank.add(type?.name ?: "")
                selection?.let {
                    if (it != 0 && type?.id == selection) {
                        selectedBank = type.index ?: -1
                    }
                }
            }

            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), R.layout.item_dropdown_area_type, bank)
            binding.bankField.setAdapter(adapter)
            if (selectedBank != -1 ) {
                val select = list[selectedBank]
                select?.let {
                    binding.bankField.setText(select.name, false)
                    bankId = select.id
                }
            }

            binding.bankField.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    Log.d(TAG, "intiCitiesSpinner: $position")
                    bankId = list[position]?.id!!
                }
        }
    }

    private fun changeBackground(selected: TextView, unselected1: TextView, unselected2: TextView) {
        selected.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.bg_white)
        selected.setTextColor(resources.getColor(R.color.black))

        unselected1.background =
            ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
        unselected1.setTextColor(resources.getColor(R.color.black))

        unselected2.background =
            ContextCompat.getDrawable(requireContext(), android.R.color.transparent)
        unselected2.setTextColor(resources.getColor(R.color.black))
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
            ExchangePointsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}