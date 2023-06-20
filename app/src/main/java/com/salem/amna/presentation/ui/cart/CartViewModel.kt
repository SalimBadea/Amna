package com.salem.amna.presentation.ui.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.add_product.AddItemUseCase
import com.salem.amna.domain.use_case.cart.CheckoutUseCase
import com.salem.amna.domain.use_case.cart.DeleteItemUseCase
import com.salem.amna.domain.use_case.cart.GetCartUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val deleteItemUseCase: DeleteItemUseCase,
    private val checkoutUseCase: CheckoutUseCase
): ViewModel() {

    private val TAG = "CartViewModel"

    private var _uiState: MutableStateFlow<CartState> = MutableStateFlow(CartState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        getCart()
    }

    private fun getCart() {
        getCartUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(
                        UiEffect.ShowToast(
                            result.data?.message ?: "An unexpected error occurred"
                        )
                    )
                    CartState(
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

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.DeleteItem -> {
                deleteItem(event.itemId)
            }
            is CartEvent.Checkout -> {
                checkout(event.where_to_deliver, event.user_address_id)
            }
            else -> {}
        }
    }

    private fun deleteItem(itemId: Int) {
            deleteItemUseCase(itemId = itemId).onEach { result ->
                _uiState.value = when (result) {
                    is Resource.Success -> {
                        getCart()
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

    private fun checkout(where_to_deliver: Int, user_address_id: Int?) {
            checkoutUseCase(where_to_deliver, user_address_id).onEach { result ->
                _uiState.value = when (result) {
                    is Resource.Success -> {
                        getCart()
                        _uiState.value.copy(
                            isConfirmed = result.data != null,
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

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }
}