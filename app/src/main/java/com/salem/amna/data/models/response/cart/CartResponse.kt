package com.salem.amna.data.models.response.cart

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.data.models.common.Governorates
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartResponse(
    @field:SerializedName("items")
    val items: MutableList<CategoryItemModel>? = null,

    @field:SerializedName("statistics")
    val statistics: Statistics? = null
): Parcelable

@Parcelize
data class Statistics(
    @field:SerializedName("items_count")
    val items_count: Int,

    @field:SerializedName("total_points")
    val total_points: Int
): Parcelable
