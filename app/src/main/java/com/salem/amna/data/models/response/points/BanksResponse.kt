package com.salem.amna.data.models.response.points

import com.google.gson.annotations.SerializedName

data class BanksResponse(
    @field:SerializedName("banks")
    val banks: MutableList<Banks>? = null,
)

data class Banks(

    @field:SerializedName("index")
    val index: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String
)