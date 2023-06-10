package com.salem.amna.data.models.response.categories

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.data.models.common.UserModel

data class CategoriesResponse (

    @field:SerializedName("categories")
    val categories: MutableList<CategoriesModel>? = null,
)

