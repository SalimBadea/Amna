package com.salem.amna.data.models.common

import com.google.gson.annotations.SerializedName

data class AddressModel(

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("lat")
    val lat: Double? = null,

    @SerializedName("lng")
    val lng: Double? = null,

    @SerializedName("governorate")
    val governorate: GovernoratesModel? = null,

    @SerializedName("city")
    val city: CitiesModel? = null
)
