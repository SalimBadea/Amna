package com.salem.amna.presentation.ui.my_account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.accountinfo.GetAccountInfoUseCase
import com.salem.amna.domain.use_case.accountinfo.UpdateAccountInfoUseCase
import com.salem.amna.domain.use_case.auth.validation.ValidateEmail
import com.salem.amna.domain.use_case.auth.validation.ValidateName
import com.salem.amna.domain.use_case.auth.validation.ValidatePhone
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AccountInfoViewModel @Inject constructor(
    private val getAccountInfoUseCase: GetAccountInfoUseCase,
    private val updateAccountInfoUseCase: UpdateAccountInfoUseCase,
    private val validateName: ValidateName,
    private val validateEmail: ValidateEmail,
    private val validatePhone: ValidatePhone,

    ) : ViewModel() {
    private val TAG = "AccountInfoViewModel"
    private var _uiState: MutableStateFlow<AccountInfoState> = MutableStateFlow(AccountInfoState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        getAccountInfo()
    }

    fun getAccountInfo() {
        getAccountInfoUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _uiState.value.copy(
                        isSuccess = result.data != null,
                        result = result.data?.data,
                        email = result.data?.data?.user?.email.toString(),
                        name = result.data?.data?.user?.name.toString(),
                        phone = result.data?.data?.user?.phone.toString(),
                        avatar = result.data?.data?.user?.image,
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "AccountInfo: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }

            }
        }.launchIn(viewModelScope)
    }

    private fun updateAccountInfo() {
        updateAccountInfoUseCase(_uiState.value.toAccountInfoBody()).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message ?: ""))
                    _uiState.value.copy(
                        isSuccess = result.data != null,
                        result = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "AccountInfo: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: AccountInfoEvent) {
        when (event) {
            is AccountInfoEvent.BirthYearChanged -> {
                _uiState.value =
                    _uiState.value.copy(birthDate = event.birthDate)
            }
            is AccountInfoEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email, emailError = null)
            }
            is AccountInfoEvent.GenderChanged -> {
                _uiState.value = _uiState.value.copy(gender = event.gender)
            }
            is AccountInfoEvent.NameChanged -> {
                _uiState.value = _uiState.value.copy(name = event.name, nameError = null)

            }
            is AccountInfoEvent.PhoneChanged -> {
                _uiState.value = _uiState.value.copy(phone = event.phone, phoneError = null)
            }
            is AccountInfoEvent.Submit -> {submitData() }

            is AccountInfoEvent.InitData -> {

            }
            else -> {}
        }
    }


    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }

    private fun submitData() {
        val emailResult = validateEmail(_uiState.value.email)
        val nameResult = validateName(_uiState.value.name)
        val phoneResult = validatePhone(_uiState.value.phone)

        val hasError = listOf(
            emailResult,
            nameResult,
            phoneResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                nameError = nameResult.errorMessage,
                emailError = emailResult.errorMessage,
                phoneError = phoneResult.errorMessage,
            )
            return
        }

        updateAccountInfo()
    }



}