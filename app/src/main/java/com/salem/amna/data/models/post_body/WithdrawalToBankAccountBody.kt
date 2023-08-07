package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class WithdrawalToBankAccountBody(
    @SerializedName("bank_id")
    val bankId: Int,

    @SerializedName("points")
    val points: String,

    @SerializedName("money")
    val money: String,

    @SerializedName("bank_account_number")
    val bankAccountNumber: String,

    @SerializedName("bank_account_name")
    val bankAccountName: String
)