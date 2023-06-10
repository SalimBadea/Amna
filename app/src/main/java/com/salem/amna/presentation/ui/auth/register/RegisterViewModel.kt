package com.salem.amna.presentation.ui.auth.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.data.repository.local.preference.LocalePreference
import com.salem.amna.domain.use_case.auth.RegisterUseCase
import com.salem.amna.domain.use_case.auth.validation.ValidateEmail
import com.salem.amna.domain.use_case.auth.validation.ValidateName
import com.salem.amna.domain.use_case.auth.validation.ValidatePassword
import com.salem.amna.domain.use_case.auth.validation.ValidatePhone
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePhone: ValidatePhone,
    private val validatePassword: ValidatePassword,
    private val localePreference: LocalePreference,
) : ViewModel() {
    private val TAG = "RegisterViewModel"
    private var _uiState: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    private var fcmToken: String? = null
    private var type: Int? = null

    init {
        viewModelScope.launch {
            fcmToken = localePreference.getFCMToken()
            type = localePreference.getAccountType()
        }
    }

    private fun register() {
        registerUseCase(
            _uiState.value.toRegisterBody().copy(accountType = type, firebaseToken = fcmToken)).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message ?: ""))
                    saveToken(result.data?.data?.token ?: "")
                    _uiState.value.copy(isSuccess = result.data != null, result = result.data?.data, isLoading = false)
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


    fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email, emailError = null)
            }
            is RegisterEvent.PasswordChanged -> {
                _uiState.value =
                    _uiState.value.copy(password = event.password, passwordError = null)
            }
            is RegisterEvent.PasswordVisibilityChange -> {
                _uiState.value = _uiState.value.copy(passwordIsVisible = event.value)
            }
            is RegisterEvent.RepeatPasswordChanged -> {
                _uiState.value = _uiState.value.copy(
                    passwordConfirmation = event.password,
                    passwordConfirmationError = null
                )
            }
            is RegisterEvent.RepeatPasswordVisibilityChange -> {
                _uiState.value = _uiState.value.copy(passwordConfirmationIsVisible = event.value)
            }
            is RegisterEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(name = event.name, nameError = null)
            }
            is RegisterEvent.PhoneChanged -> {
                _uiState.value = _uiState.value.copy(phone = event.phone, phoneError = null)
            }

            is RegisterEvent.AlreadyHaveAccount -> {
                navigate(event.directions)
            }
            is RegisterEvent.Submit -> {
                submitData()
            }
            is RegisterEvent.GenderChanged -> {
                _uiState.value = _uiState.value.copy(gender = event.gender)
            }
            is RegisterEvent.SendMailIsChecked -> {
                _uiState.value = _uiState.value.copy(
                    sendMail = event.isChecked,
                    sendMailError = null
                )
            }
            else -> {}
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
        _navigation.value = NavigationCommand.Stop
    }

    private fun submitData() {
        val emailResult = validateEmail(_uiState.value.email)
        val passwordResult = validatePassword(_uiState.value.password)
        val nameResult = validateName(_uiState.value.name)
        val phoneResult = validatePhone(_uiState.value.phone)
        val accountTypeResult = _uiState.value.accountType
        val platformResult = _uiState.value.platform
//        val sendMailResult = validateSendMail(_uiState.value.sendMail)


        val hasError = listOf(
            emailResult,
            passwordResult,
            nameResult,
            phoneResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                phoneError = phoneResult.errorMessage,
            )
            return
        }
        val uiStateValue = _uiState.value
        register()

    }

    private fun saveToken(userId: String) {
        viewModelScope.launch {
            localePreference.saveToken(userId)
        }
    }

    fun clearSuccessState() {
        _uiState.value = _uiState.value.copy(isSuccess = false, isLoading = false)

    }

}