package com.salem.amna.data.models.response.addresses

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.CitiesModel
import com.salem.amna.data.models.common.GovernoratesModel

data class GovernoratesResponse(

    @field:SerializedName("governorates")
    val governorates: MutableList<GovernoratesModel>? = null,
)

data class CitiesResponse(

    @field:SerializedName("cities")
    val cities: MutableList<CitiesModel>? = null,
)