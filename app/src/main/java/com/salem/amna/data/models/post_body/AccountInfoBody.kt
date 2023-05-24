package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class AccountInfoBody(


    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("_method")
    val _method: String = "PATCH",

)