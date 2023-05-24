package com.salem.amna.data.models.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Suppress("UNCHECKED_CAST")
class MainResponseModel<T> {

    @Expose
    @SerializedName("status")
    val status: Boolean? = null

    @Expose
    @SerializedName("message")
    val message: String? = null

    @Expose
    @SerializedName("data")
    var data:T? = null

    @Expose
    @SerializedName("errors")
    val errors: MutableList<String>? = null
}