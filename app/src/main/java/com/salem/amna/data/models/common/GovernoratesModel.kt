package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class GovernoratesModel(
    @SerializedName("index")
    val index: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String? = null
)

data class Governorates(

    @SerializedName("index")
    val index: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: Name? = null
)

data class CitiesModel(
    @SerializedName("index")
    val index: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: String? = null
)

data class Cities(
    @SerializedName("index")
    val index: Int = 0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("name")
    val name: Name? = null
)

data class Name(
    @SerializedName("ar")
    val ar: String? = null,

    @SerializedName("en")
    val en: String? = null
)