package com.salem.amna.presentation.ui.my_account.addresses.add

import androidx.navigation.NavDirections
import com.salem.amna.data.models.common.AddressModel


sealed class AddAddressEvent {

    data class FullAddressChanged(val address: String) : AddAddressEvent()
    data class FirstNameChanged(val name: String) : AddAddressEvent()
    data class LastNameChanged(val lastName: String) : AddAddressEvent()
    data class PhoneChanged(val phone: String) : AddAddressEvent()
    data class CityChanged(val city: Int) : AddAddressEvent()
    data class AreaChanged(val area: Int,val getCities:Boolean) : AddAddressEvent()
    data class TypeChanged(val type: String) : AddAddressEvent()
    data class LatChanged(val lat: String) : AddAddressEvent()
    data class LongChanged(val long: String) : AddAddressEvent()
    data class InitState(val addressModel: AddressModel) : AddAddressEvent()
    data class Skip(val directions: NavDirections) : AddAddressEvent()
    object SaveAddress : AddAddressEvent()
}