package com.salem.amna.data.models.response.points

import com.google.gson.annotations.SerializedName

data class PointsResponse(
    @field:SerializedName("points")
    val points: Int? = null,

    @field:SerializedName("money")
    val money: Double? = null,

)

data class WithdrawalsResponse(
    @field:SerializedName("points")
    val points: Int? = null,

    @field:SerializedName("money")
    val money: Double? = null,

    @SerializedName("withdrawals")
    val withdrawals: MutableList<Withdrawals>? = null
)

data class Withdrawals(
    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("type")
    val type: String? = null,
    @field:SerializedName("points")
    val points: Int? = null,
    @field:SerializedName("money")
    val money: Double? = null,
    @field:SerializedName("date")
    val date: String? = null,
)