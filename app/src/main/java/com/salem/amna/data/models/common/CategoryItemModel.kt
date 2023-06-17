package com.salem.amna.data.models.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryItemModel(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("points")
    val points: Int? = null,

    @SerializedName("category")
    val category: CategoriesModel? = null,

):Parcelable
