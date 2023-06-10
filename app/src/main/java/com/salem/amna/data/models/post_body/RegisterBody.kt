package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class RegisterBody(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("account_type")
    val accountType: Int? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("platform")
    val platform: String = "android",

    @field:SerializedName("firebase_token")
    val firebaseToken: String? = null,

)

