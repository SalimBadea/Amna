package com.salem.amna.presentation.ui.my_account.exchange_points

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.salem.amna.R
import com.salem.amna.base.BaseFragment
import com.salem.amna.data.models.post_body.WithdrawalToBankAccountBody
import com.salem.amna.data.models.post_body.WithdrawalToBankCardBody
import com.salem.amna.data.models.post_body.WithdrawalToWalletBody
import com.salem.amna.data.models.response.points.Banks
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.databinding.FragmentExchangePointsBinding
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExchangePointsFragment : BaseFragment() {

    private val TAG = "ExchangePointsFragment"

    private val binding by lazy {
        FragmentExchangePointsBinding.inflate(layoutInflater)
    }

    private val viewModel: ExchangePointsViewModel by viewModels()

    private var bankId = 0
    private var points = ""
    private var money = ""
    private var type = "Account"

    override fun getRootView(): View = binding.root

    override fun initVar() {
        viewModel.onEvent(ExchangePointsEvent.GetPoints)
        viewModel.onEvent(ExchangePointsEvent.LoadBanks)
    }

    override fun onEvent() {
        binding.backIv.setOnClickListener {
            baseActivity.onBackPressed()
        }

        binding.tvBankAccount.setOnClickListener {
            type = "Account"
            changeBackground(binding.tvBankAccount, binding.tvWallet, binding.tvBankCart)
            binding.tvCardNumberTitle.visibility = View.INVISIBLE
            binding.etCardNumber.visibility = View.INVISIBLE
            binding.tvMobileNumberTitle.visibility = View.INVISIBLE
            binding.etMobileNumber.visibility = View.INVISIBLE
            binding.etBank.visibility = View.VISIBLE
            binding.tvBankTitle.visibility = View.VISIBLE
            binding.tvAccountNumberTitle.visibility = View.VISIBLE
            binding.etAccountNumber.visibility = View.VISIBLE
            binding.tvAccountOwnerTitle.visibility = View.VISIBLE
            binding.etAccountOwner.visibility = View.VISIBLE
        }

        binding.tvWallet.setOnClickListener {
            type = "Wallet"
            changeBackground(binding.tvWallet, binding.tvBankAccount, binding.tvBankCart)
            binding.tvCardNumberTitle.visibility = View.INVISIBLE
            binding.etCardNumber.visibility = View.INVISIBLE
            binding.tvMobileNumberTitle.visibility = View.VISIBLE
            binding.etMobileNumber.visibility = View.VISIBLE
            binding.etBank.visibility = View.INVISIBLE
            binding.tvBankTitle.visibility = View.INVISIBLE
            binding.tvAccountNumberTitle.visibility = View.INVISIBLE
            binding.etAccountNumber.visibility = View.INVISIBLE
            binding.tvAccountOwnerTitle.visibility = View.INVISIBLE
            binding.etAccountOwner.visibility = View.INVISIBLE
        }

        binding.tvBankCart.setOnClickListener {
            type = "Card"
            changeBackground(binding.tvBankCart, binding.tvWallet, binding.tvBankAccount)
            binding.tvCardNumberTitle.visibility = View.VISIBLE
            binding.etCardNumber.visibility = View.VISIBLE
            binding.tvMobileNumberTitle.visibility = View.INVISIBLE
            binding.etMobileNumber.visibility = View.INVISIBLE
            binding.etBank.visibility = View.VISIBLE
            binding.tvBankTitle.visibility = View.VISIBLE
            binding.tvAccountNumberTitle.visibility = View.INVISIBLE
            binding.etAccountNumber.visibility = View.INVISIBLE
            binding.tvAccountOwnerTitle.visibility = View.INVISIBLE
            binding.etAccountOwner.visibility = View.INVISIBLE
        }

        binding.sendBtn.setOnClickListener {
            if (type == "Account") {
                viewModel.onEvent(
                    ExchangePointsEvent.WithdrawToBankAccount(
                        WithdrawalToBankAccountBody(
                            bankId,
                            points,
                            money,
                            binding.etAccountNumber.text.toString(),
                            binding.etAccountOwner.text.toString()
                        )
                    )
                )
            }else if (type == "Wallet"){
                viewModel.onEvent(
                    ExchangePointsEvent.WithdrawToWallet(
                        WithdrawalToWalletBody(
                            "bank_wallet",
                            points,
                            money,
                            binding.etMobileNumber.text.toString(),
                        )
                    )
                )
            }else {
                viewModel.onEvent(
                    ExchangePointsEvent.WithdrawToBankCard(
                        WithdrawalToBankCardBody(
                            bankId,
                            points,
                            money,
                            binding.etCardNumber.text.toString(),
                        )
                    )
                )
            }
        }
    }

    override fun render() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                if (state.isBanks) {
                    initBanks(state.banksResult?.banks, state.bankId)
                    hideLoadingDialog()
                }

                if (state.isPoints) {
                    initData(state.pointsResult!!)
                    hideLoadingDialog()
                }

                if (state.isWithdrawAccount){
                    showToast(state.message)
                    hideLoadingDialog()
                    baseActivity.onBackPressed()

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

        points = state.points.toString()
        money = state.money.toString()
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
            if (selectedBank != -1) {
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