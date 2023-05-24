package com.salem.amna.presentation.ui.my_account.contact_us

sealed class ContactUsEvent {

    data class OrderNumberChanged(val orderNumber: String) : ContactUsEvent()
    data class EmailChanged(val emailOrPhone: String) : ContactUsEvent()
    data class FullNameChanged(val name: String) : ContactUsEvent()
    data class PhoneChanged(val phone: String) : ContactUsEvent()
    data class MessageChanged(val message: String) : ContactUsEvent()
    object Submit : ContactUsEvent()
}