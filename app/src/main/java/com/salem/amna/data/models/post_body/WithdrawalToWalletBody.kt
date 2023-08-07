package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class WithdrawalToWalletBody(
    @SerializedName("wallet_issuer")
    val walletIssuer: String,

    @SerializedName("points")
    val points: String,

    @SerializedName("money")
    val money: String,

    @SerializedName("wallet_number")
    val wallet_number: String,
)