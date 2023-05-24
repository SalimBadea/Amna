package com.salem.amna.presentation.ui.my_account

import androidx.navigation.NavDirections
import com.salem.amna.data.models.common.UserModel

sealed class AccountInfoEvent {

    data class NameChanged(val name: String) : AccountInfoEvent()
    data class EmailChanged(val email: String) : AccountInfoEvent()
    data class PasswordChanged(val password: String) : AccountInfoEvent()
    data class PasswordVisibilityChange(val value: Boolean) : AccountInfoEvent()
    data class RepeatPasswordChanged(val password: String) : AccountInfoEvent()
    data class RepeatPasswordVisibilityChange(val value: Boolean) : AccountInfoEvent()
    data class PhoneChanged(val phone: String) : AccountInfoEvent()
    data class BirthYearChanged(val birthDate: String) : AccountInfoEvent()
    data class ProfileImageChanged(val profileImagePath: String) : AccountInfoEvent()
    data class GenderChanged(val gender: String) : AccountInfoEvent()
    data class TermsAndConditionsIsChecked(val isChecked: Boolean) : AccountInfoEvent()
    data class AlreadyHaveAccount(val directions: NavDirections) : AccountInfoEvent()
    object Submit : AccountInfoEvent()
    data class InitData(val state: UserModel?) : AccountInfoEvent()

}