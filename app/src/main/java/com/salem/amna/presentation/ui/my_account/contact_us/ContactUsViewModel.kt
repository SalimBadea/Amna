package com.salem.amna.presentation.ui.my_account.contact_us

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.auth.validation.ValidateMessage
import com.salem.amna.domain.use_case.auth.validation.ValidateName
import com.salem.amna.domain.use_case.auth.validation.ValidatePhone
import com.salem.amna.domain.use_case.general.ContactUsUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val contactUsUseCase: ContactUsUseCase,
    private val validateName: ValidateName,
    private val validatePhone: ValidatePhone,
    private val validateMessage: ValidateMessage,

    ) : ViewModel() {
    private val TAG = "ContactUsViewModel"
    private var _uiState: MutableStateFlow<ContactUsState> = MutableStateFlow(ContactUsState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()


    private fun contactUsUse() {
        contactUsUseCase(_uiState.value.toContactUsBody()).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    ContactUsState(
                        isSuccess = result.data != null,
                        isLoading = false,
                        result = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "ContactUs: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: ContactUsEvent) {
        when (event) {
            is ContactUsEvent.OrderNumberChanged -> {
                _uiState.value = _uiState.value.copy(email = event.orderNumber, orderNumberError = null)
            }
            is ContactUsEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.emailOrPhone, emailError = null)
            }
            is ContactUsEvent.FullNameChanged -> {
                _uiState.value = _uiState.value.copy(name = event.name, nameError = null)
            }
            is ContactUsEvent.MessageChanged -> {
                _uiState.value = _uiState.value.copy(message = event.message, messageError = null)
            }
            is ContactUsEvent.PhoneChanged -> {
                _uiState.value = _uiState.value.copy(phone = event.phone, phoneError = null)
            }
            is ContactUsEvent.Submit -> {
                submitData()
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }

    private fun submitData() {
        val nameResult = validateName(_uiState.value.name)
        val phoneResult = validatePhone(_uiState.value.phone)
        val messageResult = validateMessage(_uiState.value.message)

        val hasError = listOf(
            nameResult,
            phoneResult,
            messageResult,
        ).any { !it.successful }

        if (hasError) {
            _uiState.value = _uiState.value.copy(
                nameError = nameResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                messageError = messageResult.errorMessage,
            )
            return
        }

        contactUsUse()
    }
}