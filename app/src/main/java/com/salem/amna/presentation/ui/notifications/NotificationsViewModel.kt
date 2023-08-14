package com.salem.amna.presentation.ui.notifications

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.addresses.DeleteAddressUseCase
import com.salem.amna.domain.use_case.addresses.GetAddressesUseCase
import com.salem.amna.domain.use_case.notifications.GetNotificationsUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {
    private val TAG = "NotificationsViewModel"
    private var _uiState: MutableStateFlow<NotificationsState> = MutableStateFlow(NotificationsState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        getNotifications()
    }

    private fun getNotifications() {
        getNotificationsUseCase().onEach { result ->
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

    fun onEvent(event: NotificationsEvent) {
        when (event) {
            is NotificationsEvent.Submit -> {
                getNotifications()
            }
            else -> {}
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }


}