package com.salem.amna.presentation.ui.my_account.contact_us

import com.salem.amna.data.models.post_body.ContactUsBody
import com.salem.amna.data.models.response.general.ContactUsResponse

data class ContactUsState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val result: ContactUsResponse? = null,
    val error: String = "",
    val orderNumber: String? = null,
    val orderNumberError: Int? = null,
    val name: String = "",
    val nameError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val phone: String = "",
    val phoneError: Int? = null,
    val message: String = "",
    val messageError: Int? = null,

    ) {

    fun toContactUsBody() = ContactUsBody(
        phone = phone,
        name = name,
        notes = message
    )
}
