package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class CategoryItemModel(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("points")
    val points: Int? = null,

    @SerializedName("category")
    val category: CategoriesModel? = null,

)
