package com.salem.amna.presentation.ui.my_account.about_us

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.general.GetAboutUseCase
import com.salem.amna.domain.use_case.general.GetPrivacyUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PagesViewModel @Inject constructor(
    private val getAboutUseCase: GetAboutUseCase,
    private val getPrivacyUseCase: GetPrivacyUseCase,

    ) : ViewModel() {
    private val TAG = "AboutUsViewModel"
    private var _uiState: MutableStateFlow<PagesState> = MutableStateFlow(PagesState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()


    private fun getAboutUs() {
        getAboutUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    PagesState(
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

    private fun getPrivacy() {
        getPrivacyUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    PagesState(
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


    fun onEvent(event: PagesEvent) {
        when (event) {
            is PagesEvent.GetAbout -> {
                getAboutUs()
            }
            is PagesEvent.GetPrivacy -> {
                getPrivacy()
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }

}