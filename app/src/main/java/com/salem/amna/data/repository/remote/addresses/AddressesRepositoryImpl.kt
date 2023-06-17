package com.salem.amna.data.repository.remote.addresses

import com.salem.amna.data.apiservice.addresses.AddressesServices
import com.salem.amna.data.models.post_body.AddressBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.domain.repository.addresses.AddressesRepository
import retrofit2.Response
import javax.inject.Inject

class AddressesRepositoryImpl @Inject constructor(
    val api: AddressesServices
): AddressesRepository {
    override suspend fun getGovernorates(): Response<MainResponseModel<GovernoratesResponse>> =
        api.getGovernorates()

    override suspend fun getCities(governorateId: Int): Response<MainResponseModel<CitiesResponse>> =
        api.getCities(governorateId)

    override suspend fun getAddresses(): Response<MainResponseModel<AddressesResponse>> =
        api.getAddresses()

    override suspend fun addNewAddress(addressBody: AddressBody): Response<MainResponseModel<ContactUsResponse>> =
        api.AddNewAddress(addressBody)

    override suspend fun updateAddress(
        addressId: Int,
        addressBody: AddressBody
    ): Response<MainResponseModel<ContactUsResponse>> =
        api.UpdateAddress(addressId, addressBody)

    override suspend fun deleteAddress(
        addressId: Int,
        method: String
    ): Response<MainResponseModel<ContactUsResponse>> = api.deleteAddress(addressId, method)
}