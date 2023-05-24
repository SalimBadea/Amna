package com.salem.amna.presentation.ui.auth.sharedviewmodel

import androidx.lifecycle.ViewModel
import com.salem.amna.presentation.ui.auth.register.RegisterState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthSharedViewModel : ViewModel() {
    private var _registerSharedData: MutableStateFlow<RegisterState> =
        MutableStateFlow(RegisterState())
    val registerSharedData = _registerSharedData.asStateFlow()

    private var _forgetPasswordEmail: MutableStateFlow<String?> = MutableStateFlow(null)
    val forgetPasswordEmail = _forgetPasswordEmail.asStateFlow()

    private var _code: MutableStateFlow<Int?> =
        MutableStateFlow(null)
    val code = _code.asStateFlow()

    fun setRegisterData(state: RegisterState) {
        _registerSharedData.value = state
    }

    fun setForgetPasswordEmail(email: String) {
        _forgetPasswordEmail.value = email
    }

    fun setCode(code: Int?) {
        _code.value = code
    }
}