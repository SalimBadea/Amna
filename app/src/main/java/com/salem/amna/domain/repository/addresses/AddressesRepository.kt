package com.salem.amna.domain.repository.addresses

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import retrofit2.Response

interface AddressesRepository {

    suspend fun getGovernorates(): Response<MainResponseModel<GovernoratesResponse>>

    suspend fun getCities(governorateId: Int): Response<MainResponseModel<CitiesResponse>>

    suspend fun getAddresses(): Response<MainResponseModel<AddressesResponse>>
}