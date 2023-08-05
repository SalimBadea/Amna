package com.salem.amna.presentation.ui.my_account.exchange_points

import com.salem.amna.data.models.response.categories.CategoriesResponse

sealed class ExchangePointsEvent {

    object LoadBanks : ExchangePointsEvent()
    object GetPoints : ExchangePointsEvent()
    class GetWithdrawals(val type: String?) : ExchangePointsEvent()
}