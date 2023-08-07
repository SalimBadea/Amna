package com.salem.amna.presentation.ui.my_account.exchange_points

import com.salem.amna.data.models.post_body.WithdrawalToBankAccountBody
import com.salem.amna.data.models.post_body.WithdrawalToBankCardBody
import com.salem.amna.data.models.post_body.WithdrawalToWalletBody

sealed class ExchangePointsEvent {

    object LoadBanks : ExchangePointsEvent()
    object GetPoints : ExchangePointsEvent()
    class GetWithdrawals(val type: String?) : ExchangePointsEvent()
    class WithdrawToBankAccount(val body: WithdrawalToBankAccountBody): ExchangePointsEvent()
    class WithdrawToBankCard(val body: WithdrawalToBankCardBody): ExchangePointsEvent()
    class WithdrawToWallet(val body: WithdrawalToWalletBody): ExchangePointsEvent()
}