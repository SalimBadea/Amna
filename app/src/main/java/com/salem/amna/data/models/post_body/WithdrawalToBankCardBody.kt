package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class WithdrawalToBankCardBody(
    @SerializedName("bank_id")
    val bankId: Int,

    @SerializedName("points")
    val points: String,

    @SerializedName("money")
    val money: String,

    @SerializedName("bank_card_number")
    val bankCardNumber: String,
)