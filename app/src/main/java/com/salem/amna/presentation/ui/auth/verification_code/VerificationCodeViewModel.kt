package com.salem.amna.presentation.ui.auth.verification_code

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.domain.use_case.auth.RegisterUseCase
import com.salem.amna.domain.use_case.auth.VerifyUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class VerificationCodeViewModel @Inject constructor(
    private val verifyUseCase: VerifyUseCase,
    private val registerUseCase: RegisterUseCase,
    private val localePreference: LocalePreference,
) : ViewModel() {
    private val TAG = "VerificationCodeViewMod"
    private var _uiState: MutableStateFlow<VerificationCodeState> =
        MutableStateFlow(VerificationCodeState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()
    private var fcmToken: String? = null

    init {
        viewModelScope.launch {
            fcmToken = localePreference.getFCMToken()
        }
    }

    private fun submitRegister(code: String) {
        verifyUseCase(code).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message ?: ""))
                    saveToken()
                    _uiState.value.copy(isSuccess = result.data != null, isLoading = false)
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "register: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }
    fun resendRegisterCode() {
        registerUseCase( _uiState.value.toRegisterBody()).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message ?: ""))

                    _uiState.value.copy(isUpdate = result.data != null, result = result.data?.data , isLoading = false)
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "register: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: VerificationCodeEvent) {
        when (event) {
           is VerificationCodeEvent.ResendCode -> {

            }
            is VerificationCodeEvent.Confirm -> {
                submitData(event.code)
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
        _navigation.value = NavigationCommand.Stop

    }

    private fun submitData(code:String) {

        submitRegister(code)
    }

    private fun saveToken() {
        runBlocking{
            localePreference.setLoginState(true)
        }
    }


}