package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("password")
    val password: String? = null,
)
