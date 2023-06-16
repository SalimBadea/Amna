package com.salem.amna.presentation.ui.my_account.addresses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.addresses.DeleteAddressUseCase
import com.salem.amna.domain.use_case.addresses.GetAddressesUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val deleteAddressUseCase: DeleteAddressUseCase,
) : ViewModel() {
    private val TAG = "AddressViewModel"
    private var _uiState: MutableStateFlow<AddressState> = MutableStateFlow(AddressState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()


    fun addresses() {
        getAddressesUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _uiState.value.copy(
                        isSuccess = result.data != null,
                        result = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "Address: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteAddress(addressId: Int) {
        deleteAddressUseCase(addressId = addressId).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    addresses()
                    _uiState.value.copy(
                        isSuccess = result.data != null,
                        isLoading = false,
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "Address: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }

//    private fun makeDefaultAddress(addressId: Int) {
//        makeDefaultAddressUseCase(addressId = addressId).onEach { result ->
//            _uiState.value = when (result) {
//                is Resource.Success -> {
//                    addresses()
//                    _uiState.value.copy(
//                        isSuccess = result.data != null,
//                        isLoading = false,
//                    )
//                }
//                is Resource.Error -> {
//                    val message = result.message ?: "An unexpected error occurred"
//                    Log.e(TAG, "Address: error message $message")
//                    _effect.send(UiEffect.ShowToast(message))
//                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
//                }
//                is Resource.Loading -> {
//                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }


    fun onEvent(event: AddressEvent) {
        when (event) {
            is AddressEvent.DeleteAddress -> {
                deleteAddress(event.addressId)
            }
//            is AddressEvent.MakeDefaultAddress -> makeDefaultAddress(event.addressId)
            else -> {}
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }


}