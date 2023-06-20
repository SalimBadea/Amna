package com.salem.amna.presentation.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.auth.validation.ValidateMessage
import com.salem.amna.domain.use_case.auth.validation.ValidateName
import com.salem.amna.domain.use_case.auth.validation.ValidatePhone
import com.salem.amna.domain.use_case.categories.GetCategoriesUseCase
import com.salem.amna.domain.use_case.categories.GetCategoryItemsUseCase
import com.salem.amna.domain.use_case.home.GetHomeUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase
) : ViewModel() {

    private val TAG = "HomeViewModel"
    private var _uiState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()


    fun getHome(status: Int?) {
        getHomeUseCase(status = status).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(
                        UiEffect.ShowToast(
                            result.data?.message ?: "An unexpected error occurred"
                        )
                    )
                    HomeState(
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




    fun onEvent(event: HomeEvent) {
        when (event) {


            else -> {}
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }

}