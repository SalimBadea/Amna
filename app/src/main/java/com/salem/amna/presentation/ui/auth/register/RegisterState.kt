package com.salem.amna.presentation.ui.auth.register

import android.net.Uri
import com.salem.amna.data.models.post_body.RegisterBody
import com.salem.amna.data.models.response.auth.RegisterResponse

data class RegisterState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val result: RegisterResponse? = null,
    val error: String = "",
    val name: String = "",
    val nameError: Int? = null,
    val email: String = "",
    val emailError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val passwordIsVisible: Boolean = false,
    val passwordConfirmation: String = "",
    val passwordConfirmationError: Int? = null,
    val passwordConfirmationIsVisible: Boolean = false,
    val phone: String = "",
    val phoneError: Int? = null,
    val gender: String = "male",
    val accountType: Int = 1,
    val accountTypeError: Int? = null,
    val platform: String = "android",
    val platformError: Int? = null,
    val sendMail: Boolean = false,
    val sendMailError: Int? = null,
    val firebase_token: String? = null,
    val imageUri: Uri? = null,
){
    fun toRegisterBody() = RegisterBody(
        email = email,
        phone = phone,
        name = name,
        password = password,
        accountType = accountType,
        platform = platform,
        firebaseToken = firebase_token,

    )
}
