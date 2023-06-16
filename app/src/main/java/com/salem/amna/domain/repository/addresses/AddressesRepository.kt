package com.salem.amna.domain.repository.addresses

import com.salem.amna.data.models.post_body.AddressBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import retrofit2.Response

interface AddressesRepository {

    suspend fun getGovernorates(): Response<MainResponseModel<GovernoratesResponse>>

    suspend fun getCities(governorateId: Int): Response<MainResponseModel<CitiesResponse>>

    suspend fun getAddresses(): Response<MainResponseModel<AddressesResponse>>

    suspend fun addNewAddress(addressBody: AddressBody): Response<MainResponseModel<ContactUsResponse>>

    suspend fun deleteAddress(addressId:Int, method: String): Response<MainResponseModel<ContactUsResponse>>

}