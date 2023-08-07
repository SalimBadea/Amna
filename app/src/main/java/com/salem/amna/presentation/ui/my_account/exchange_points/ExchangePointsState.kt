package com.salem.amna.presentation.ui.my_account.exchange_points

import com.salem.amna.data.models.post_body.ContactUsBody
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.points.BanksResponse
import com.salem.amna.data.models.response.points.PointsResponse
import com.salem.amna.data.models.response.points.Withdrawals
import com.salem.amna.data.models.response.points.WithdrawalsResponse

data class ExchangePointsState(
    val isLoading: Boolean = false,
    val isPoints: Boolean = false,
    val isWithdrawals: Boolean = false,
    val isBanks: Boolean = false,
    val isWithdrawAccount: Boolean = false,
    val isWithdrawCard: Boolean = false,
    val isWithdrawWallet: Boolean = false,
    val banksResult: BanksResponse? = null,
    val pointsResult: PointsResponse? = null,
    val withdrawalsResult: WithdrawalsResponse? = null,
    val withdrawBankAccountResult: ContactUsResponse? = null,
    val error: String = "",
    val orderNumber: String? = null,
    val orderNumberError: Int? = null,

    val bankId: Int? = null,
    val bankError: Int? = null,

    val name: String = "",
    val nameError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val phone: String = "",
    val phoneError: Int? = null,
    val message: String = "",
    val messageError: Int? = null,
)
