package com.salem.amna.presentation.ui.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.domain.use_case.auth.LoginUseCase
import com.salem.amna.domain.use_case.auth.validation.ValidateEmailOrPassword
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmail: ValidateEmailOrPassword,
    private val validatePassword: ValidatePassword,
    private val localePreference: LocalePreference,
) : ViewModel() {
    private val TAG = "LoginViewModel"
    private var _uiState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
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

    private fun login() {
        loginUseCase(_uiState.value.toLoginBody().copy(fcmToken = fcmToken)).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    saveToken(result.data?.data?.accessToken ?: "")

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


    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.PhoneChanged -> {
                _uiState.value = _uiState.value.copy(phone = event.phone, phoneError = null)
            }
            is LoginEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password, passwordError = null)
            }
            is LoginEvent.PasswordVisibilityChange -> {
                _uiState.value = _uiState.value.copy(passwordIsVisible = event.value)
            }

            is LoginEvent.Submit -> {
                submitData()
            }
            is LoginEvent.CreateAccount -> {
                navigate(event.directions)
            }
            is LoginEvent.ForgetPassword -> {
                navigate(event.directions)
            }
            is LoginEvent.Skip -> {
                navigate(event.directions)
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
        _navigation.value = NavigationCommand.Stop
    }

    private fun submitData() {
        val emailResult = validateEmail(_uiState.value.phone)
        val passwordResult = validatePassword(_uiState.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                phoneError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
            )
            return
        }
        login()
    }

    private fun saveToken(userId: String) {
        runBlocking{
            localePreference.setLoginState(true)
            localePreference.saveToken(userId)
        }
    }

}