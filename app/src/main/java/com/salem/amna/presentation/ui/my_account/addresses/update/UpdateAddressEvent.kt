package com.salem.amna.presentation.ui.my_account.addresses.update

import androidx.navigation.NavDirections


sealed class UpdateAddressEvent {

    data class FullAddressChanged(val address: String) : UpdateAddressEvent()
    data class FirstNameChanged(val name: String) : UpdateAddressEvent()
    data class LastNameChanged(val lastName: String) : UpdateAddressEvent()
    data class PhoneChanged(val phone: String) : UpdateAddressEvent()
    data class TypeChanged(val type: String) : UpdateAddressEvent()
    data class LatChanged(val lat: String) : UpdateAddressEvent()
    data class LongChanged(val long: String) : UpdateAddressEvent()
    data class CityChanged(val city: Int) : UpdateAddressEvent()
    data class AreaChanged(val area: Int,val getCities:Boolean) : UpdateAddressEvent()
    data class Skip(val directions: NavDirections) : UpdateAddressEvent()
    object SaveAddress : UpdateAddressEvent()
}