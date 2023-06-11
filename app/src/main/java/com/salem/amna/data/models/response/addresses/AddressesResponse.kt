package com.salem.amna.data.models.response.addresses

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.AddressModel

data class AddressesResponse (

    @field:SerializedName("addresses")
    val addresses: MutableList<AddressModel>? = null,
)

