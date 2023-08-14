package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class NotificationsModel(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("content")
    val content: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("time")
    val time: String? = null,
)