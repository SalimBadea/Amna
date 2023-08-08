package com.salem.amna.data.models.response.pages

import com.google.gson.annotations.SerializedName

data class PagesResponse(
    @SerializedName("about")
    val about: About? = null,

    @SerializedName("privacy")
    val privacy: Privacy? = null
)


data class Privacy(
    @SerializedName("content")
    val content: String,
)

data class About(
    @SerializedName("content")
    val content: String,
    @SerializedName("facebook")
    val facebook: String,
    @SerializedName("telegram")
    val telegram: String,
    @SerializedName("instagram")
    val instagram: String,
    @SerializedName("whats")
    val whats: String,
    @SerializedName("tiwtter")
    val twitter: String
)

