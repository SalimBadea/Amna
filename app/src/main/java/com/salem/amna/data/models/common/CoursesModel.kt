package com.salem.amna.data.models.common

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoursesModel(

    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("link")
    val link: String? = null,

    @SerializedName("title")
    val title: String? = null,
):Parcelable
