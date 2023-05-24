package com.salem.amna.presentation.ui.auth.verification_code

import com.salem.amna.data.models.post_body.RegisterBody
import com.salem.amna.data.models.post_body.ChangePasswordBody
import com.salem.amna.data.models.response.auth.RegisterResponse

data class VerificationCodeState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isUpdate: Boolean = false,
    val result: RegisterResponse? = null,
    val error: String = "",
    val name: String = "",
    val email: String = "",
    val code: String = "",
    val oldPassword: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val phone: String = "",
    val avatar: String? = "",
    val gender: String = "male",
    val birthDate: String = "",
    val termsAndConditions: Boolean = false,
    val sendMail: Boolean = false,
) {
    fun toSubmitRegisterBody() = ChangePasswordBody(
        current_password = oldPassword,
        password = password,
        passwordConfirmation = passwordConfirmation,
    )

    fun toRegisterBody() = RegisterBody(
        email = email,
        phone = phone,
        name = name,
        password = password,
    )

}
