package com.salem.amna.presentation.ui.my_account.change_password

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.domain.use_case.auth.ChangePasswordUseCase
import com.salem.amna.domain.use_case.auth.LoginUseCase
import com.salem.amna.domain.use_case.auth.validation.ValidateEmailOrPassword
import com.salem.amna.domain.use_case.auth.validation.ValidateMatchPassword
import com.salem.amna.domain.use_case.auth.validation.ValidatePassword
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
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val validatePassword: ValidatePassword,
    private val validateMatchPassword: ValidateMatchPassword,
    private val localePreference: LocalePreference,
) : ViewModel() {
    private val TAG = "LoginViewModel"
    private var _uiState: MutableStateFlow<ChangePasswordState> = MutableStateFlow(ChangePasswordState())
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

    private fun changePassword() {
        changePasswordUseCase(_uiState.value.toChangePasswordBody()).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(
                        UiEffect.ShowToast(
                            result.data?.message ?: "An unexpected error occurred"
                        )
                    )
                    _uiState.value.copy(isSuccess = result.data != null, isLoading = false)
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "login: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            is ChangePasswordEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password, passwordError = null)
            }
            is ChangePasswordEvent.NewPasswordChanged -> {
                _uiState.value = _uiState.value.copy(newPassword = event.newPassword, newPasswordError = null)
            }
            is ChangePasswordEvent.ConfirmNewPasswordChanged -> {
                _uiState.value = _uiState.value.copy(confirmNewPassword = event.confirmPassword, confirmNewPasswordError = null)
            }

            is ChangePasswordEvent.Submit -> {
                submitData()
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
        _navigation.value = NavigationCommand.Stop
    }

    private fun submitData() {
        val passwordResult = validatePassword(_uiState.value.password)
        val newPasswordResult = validatePassword(_uiState.value.password)
        val confirmNewPasswordResult = validateMatchPassword(_uiState.value.newPassword, _uiState.value.confirmNewPassword)

        val hasError = listOf(
            passwordResult,
            newPasswordResult,
            confirmNewPasswordResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                passwordError = passwordResult.errorMessage,
                newPasswordError = newPasswordResult.errorMessage,
                confirmNewPasswordError = confirmNewPasswordResult.errorMessage,
            )
            return
        }
        changePassword()
    }

}