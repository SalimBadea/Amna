package com.salem.amna.presentation.ui.auth.login

import androidx.navigation.NavDirections

sealed class LoginEvent {

    data class PhoneChanged(val phone: String) : LoginEvent()
    data class PasswordChanged(val password: String) : LoginEvent()
    data class PasswordVisibilityChange(val value: Boolean) : LoginEvent()
    data class  ForgetPassword(val directions: NavDirections) : LoginEvent()
    data class  CreateAccount(val directions: NavDirections) : LoginEvent()
    data class  Skip(val directions: NavDirections) : LoginEvent()
    object Submit : LoginEvent()
}