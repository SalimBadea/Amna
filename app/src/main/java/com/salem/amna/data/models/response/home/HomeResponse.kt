package com.salem.amna.data.models.response.home

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @field:SerializedName("slides")
    val slides: MutableList<Slider>,

    @field:SerializedName("leftovers")
    val leftovers: MutableList<Slider>,

    @field:SerializedName("last_leftovers")
    val last_leftovers: MutableList<Slider>,
)

data class Slider(
    @field:SerializedName("image")
    val image: String
)