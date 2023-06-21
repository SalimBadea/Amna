package com.salem.amna.data.models.response.home

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.OrderModel

data class HomeResponse(
    @field:SerializedName("slides")
    val slides: MutableList<Slider>,

    @field:SerializedName("leftovers")
    val leftovers: MutableList<OrderModel>,

    @field:SerializedName("last_leftovers")
    val last_leftovers: MutableList<OrderModel>,
)

data class Slider(
    @field:SerializedName("image")
    val image: String
)