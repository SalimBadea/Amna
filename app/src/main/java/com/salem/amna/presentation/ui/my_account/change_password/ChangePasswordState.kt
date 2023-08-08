package com.salem.amna.presentation.ui.my_account.change_password

import com.salem.amna.data.models.post_body.ChangePasswordBody
import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.general.ContactUsResponse

data class ChangePasswordState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = "",

    val password: String = "",
    val passwordError: Int? = null,
    val newPassword: String = "",
    val newPasswordError: Int? = null,
    val confirmNewPassword: String = "",
    val confirmNewPasswordError: Int? = null,
    val passwordIsVisible: Boolean = false,
){
    fun toChangePasswordBody() = ChangePasswordBody(password, newPassword, confirmNewPassword)
}
