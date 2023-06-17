package com.salem.amna.data.apiservice.addresses

import com.salem.amna.data.models.post_body.AddressBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AddressesServices {

    @GET("governorates")
    suspend fun getGovernorates(): Response<MainResponseModel<GovernoratesResponse>>

    @GET("cities")
    suspend fun getCities(@Query("governorate_id") governorateId: Int): Response<MainResponseModel<CitiesResponse>>

    @GET("addresses")
    suspend fun getAddresses(): Response<MainResponseModel<AddressesResponse>>

    @POST("addresses")
    suspend fun AddNewAddress(
        @Body addressBody: AddressBody
    ): Response<MainResponseModel<ContactUsResponse>>

    @POST("addresses/{id}")
    suspend fun UpdateAddress(
        @Path("id") addressId: Int,
        @Body addressBody: AddressBody
    ): Response<MainResponseModel<ContactUsResponse>>

    @POST("addresses/{id}")
    suspend fun deleteAddress(
        @Path("id") addressId: Int,
        @Query("_method") method: String = "DELETE"
    ): Response<MainResponseModel<ContactUsResponse>>
}