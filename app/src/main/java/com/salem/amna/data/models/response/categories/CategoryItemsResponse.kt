package com.salem.amna.data.models.response.categories

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.CategoriesModel
import com.salem.amna.data.models.common.CategoryItemModel
import com.salem.amna.data.models.common.UserModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CategoryItemsResponse (
    @field:SerializedName("items")
    val items: MutableList<CategoryItemModel>? = null,
):Parcelable

