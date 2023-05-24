package com.salem.amna.presentation.ui.auth.register

import androidx.navigation.NavDirections

sealed class RegisterEvent {

    data class NameChanged(val name: String) : RegisterEvent()
    data class EmailChanged(val email: String) : RegisterEvent()
    data class PasswordChanged(val password: String) : RegisterEvent()
    data class PasswordVisibilityChange(val value: Boolean) : RegisterEvent()
    data class RepeatPasswordChanged(val password: String) : RegisterEvent()
    data class RepeatPasswordVisibilityChange(val value: Boolean) : RegisterEvent()
    data class PhoneChanged(val phone: String) : RegisterEvent()
    data class BirthYearChanged(val birthDate: String) : RegisterEvent()
    data class GenderChanged(val gender: String) : RegisterEvent()
    data class TermsAndConditionsIsChecked(val isChecked: Boolean) : RegisterEvent()
    data class SendMailIsChecked(val isChecked: Boolean) : RegisterEvent()
    data class AlreadyHaveAccount(val directions: NavDirections) : RegisterEvent()
    object Submit : RegisterEvent()
}