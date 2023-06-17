package com.salem.amna.presentation.ui.add_product

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salem.amna.domain.use_case.add_product.GetBrandsUseCase
import com.salem.amna.domain.use_case.add_product.GetStatusesUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.cart.CartEvent
import com.salem.amna.presentation.ui.cart.CartState
import com.salem.amna.presentation.ui.my_account.addresses.add.AddAddressEvent
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val getBrandsUseCase: GetBrandsUseCase,
    private val getStatusesUseCase: GetStatusesUseCase
): ViewModel()  {

    private val TAG = "AddProductViewModel"

    private var _uiState: MutableStateFlow<AddProductState> = MutableStateFlow(AddProductState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: AddProductEvent) {
        when (event) {
            is AddProductEvent.BrandChanged -> {
               _uiState.value = _uiState.value.copy(brandId = event.brandId)

            }
            is AddProductEvent.StatusesChanged -> {
                _uiState.value = _uiState.value.copy(statusId = event.statusId)
            }
            else -> {}
        }
    }

    fun getBrands(productId:Int) {
        getBrandsUseCase(productId).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _uiState.value.copy(
                        isSuccessful = result.data != null,
                        resultBrands = result.data?.data
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

    fun getStatuses() {
        getStatusesUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _uiState.value.copy(
                        isSuccessful = result.data != null,
                        resultStatuses = result.data?.data
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
}