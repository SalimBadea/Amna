package net.moltaqa.talab_client.data.repository.remote.address

import net.moltaqa.talab_client.data.apiservice.address.AddressService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.postbody.AddressBody
import net.moltaqa.talab_client.data.models.response.address.AddNewAddressResponse
import net.moltaqa.talab_client.data.models.response.address.AddressesResponse
import net.moltaqa.talab_client.domin.repository.address.AddressRepository
import retrofit2.Response
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val api: AddressService
) : AddressRepository {

    override suspend fun getAddresses(): Response<MainResponseModel<AddressesResponse>> =
        api.getAddresses()

    override suspend fun addNewAddress(addressBody: AddressBody): Response<MainResponseModel<AddNewAddressResponse>> =
        api.addNewAddress(addressBody)

    override suspend fun updateAddress(addressId:Int,addressBody: AddressBody): Response<MainResponseModel<AddNewAddressResponse>> =
        api.updateAddress(addressId,addressBody)

    override suspend fun deleteAddress(addressId: Int): Response<MainResponseModel<AddNewAddressResponse>> =
        api.deleteAddress(addressId)

    override suspend fun makeDefaultAddress(addressId: Int): Response<MainResponseModel<AddNewAddressResponse>> =
        api.makeDefaultAddress(addressId)


}