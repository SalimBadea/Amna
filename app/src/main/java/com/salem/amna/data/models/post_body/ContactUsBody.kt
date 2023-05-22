package com.salem.amna.data.models.post_body

import com.google.gson.annotations.SerializedName

data class ContactUsBody(
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("notes")
    val notes: String? = null,
)
