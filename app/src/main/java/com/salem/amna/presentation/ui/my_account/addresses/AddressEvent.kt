package com.salem.amna.presentation.ui.my_account.addresses

sealed class AddressEvent {

    data class DeleteAddress(val addressId: Int) : AddressEvent()
    data class MakeDefaultAddress(val addressId: Int) : AddressEvent()
    object Submit : AddressEvent()
}