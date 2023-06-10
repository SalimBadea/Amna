package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class CategoriesModel(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("image")
    val image: String? = null,
)
