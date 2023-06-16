package com.salem.amna.presentation.ui.my_account.addresses.add

import com.salem.amna.data.models.post_body.AddressBody
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse

data class AddAddressState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String = "",
    val result: ContactUsResponse? = null,
    val resultAreas: GovernoratesResponse? = null,
    val resultCities: CitiesResponse? = null,
    val fullAddress: String? = null,
    val latitude: String? = null,
    val longitude: String? = null,
    val name: String = "",
    val nameError: Int? = null,
    val lastName: String = "",
    val lastNameError: Int? = null,
    val phone: String = "",
    val phoneError: Int? = null,
//    val addressType: String = ADDRESS_HOME,
    val areaId: Int? = null,
    val areaError: Int? = null,
    val cityId: Int? = null,
    val cityError: Int? = null,
) {
    fun toAddressBody() = AddressBody(
        address = fullAddress,
        latitude = latitude,
        longitude = longitude,
        governorate_id = areaId,
        city_id = cityId
    )
}
