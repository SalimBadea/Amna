package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class OrderModel(
    @SerializedName("id")
    val id: Int,

    @SerializedName("number")
    val number: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("status")
    val status: Int

)
