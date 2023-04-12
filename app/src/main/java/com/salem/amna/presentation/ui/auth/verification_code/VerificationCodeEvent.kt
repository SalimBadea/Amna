package com.salem.amna.presentation.ui.auth.verification_code


sealed class VerificationCodeEvent {

    object ResendCode : VerificationCodeEvent()
    data class Confirm(val code: String) : VerificationCodeEvent()
}