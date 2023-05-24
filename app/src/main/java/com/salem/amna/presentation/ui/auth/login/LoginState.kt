package com.salem.amna.presentation.ui.auth.login

import com.salem.amna.data.models.post_body.LoginBody

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = "",

    val phone: String = "",
    val phoneError: Int? = null,
    val password: String = "",
    val passwordError: Int? = null,
    val passwordIsVisible: Boolean = false,
){
    fun toLoginBody() = LoginBody(phone,password)
}
