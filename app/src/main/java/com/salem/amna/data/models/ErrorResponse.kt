package com.salem.amna.data.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("message")
    val message: String? = null,


    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("info")
    val info: String? = null
)
