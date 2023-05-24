package com.salem.amna.data.models

import com.google.gson.annotations.SerializedName

data class ErrorResponse(

    @field:SerializedName("data")
    var data: Any? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("errors")
    val errors: MutableList<String>? = null
)
