package com.salem.amna.data.models.response.add_product

import com.google.gson.annotations.SerializedName

data class BrandsResponse(
    @field:SerializedName("brands")
    val brands: MutableList<Brands>? = null,
)

data class Brands(

    @field:SerializedName("index")
    val index: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String
)

data class StatusesResponse(
    @field:SerializedName("items_statuses")
    val items_statuses: MutableList<Status>? = null,
)

data class Status(

    @field:SerializedName("index")
    val index: Int,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String
)

