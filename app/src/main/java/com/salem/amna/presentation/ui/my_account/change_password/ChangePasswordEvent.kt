package com.salem.amna.presentation.ui.my_account.change_password

import androidx.navigation.NavDirections

sealed class ChangePasswordEvent {
    data class PasswordChanged(val password: String) : ChangePasswordEvent()
    data class NewPasswordChanged(val newPassword: String) : ChangePasswordEvent()
    data class ConfirmNewPasswordChanged(val confirmPassword: String) : ChangePasswordEvent()
    object Submit : ChangePasswordEvent()
}