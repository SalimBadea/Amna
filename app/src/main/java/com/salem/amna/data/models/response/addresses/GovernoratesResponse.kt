package com.salem.amna.data.models.response.addresses

import com.google.gson.annotations.SerializedName
import com.salem.amna.data.models.common.Cities
import com.salem.amna.data.models.common.CitiesModel
import com.salem.amna.data.models.common.Governorates
import com.salem.amna.data.models.common.GovernoratesModel

data class GovernoratesResponse(

    @field:SerializedName("governorates")
    val governorates: MutableList<Governorates>? = null,
)

data class CitiesResponse(

    @field:SerializedName("cities")
    val cities: MutableList<Cities>? = null,
)