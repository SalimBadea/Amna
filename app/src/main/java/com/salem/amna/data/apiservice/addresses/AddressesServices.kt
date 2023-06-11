package com.salem.amna.data.apiservice.addresses

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressesServices {

    @GET("governorates")
    suspend fun getGovernorates(): Response<MainResponseModel<GovernoratesResponse>>

    @GET("Cities")
    suspend fun getCities(@Query("governorate_id") governorateId: Int): Response<MainResponseModel<CitiesResponse>>

    @GET("addresses")
    suspend fun getAddresses(): Response<MainResponseModel<AddressesResponse>>
}