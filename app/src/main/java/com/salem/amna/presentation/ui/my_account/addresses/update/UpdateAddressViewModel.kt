package com.salem.amna.presentation.ui.my_account.addresses.update

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.domain.use_case.addresses.GetCitiesUseCase
import com.salem.amna.domain.use_case.addresses.GetGovernorateUseCase
import com.salem.amna.domain.use_case.addresses.UpdateAddressUseCase
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.presentation.ui.my_account.addresses.add.AddAddressEvent
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class UpdateAddressViewModel @Inject constructor(
    private val updateAddressUseCase: UpdateAddressUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getAreasUseCase: GetGovernorateUseCase,

    ) : ViewModel() {
    private val TAG = "UpdateAddressViewModel"
    private var _uiState: MutableStateFlow<UpdateAddressState> = MutableStateFlow(UpdateAddressState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        getAreas()
    }

    private fun saveAddress() {
        updateAddressUseCase(_uiState.value.id?:0,_uiState.value.toAddressBody()).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(
                        UiEffect.ShowToast(
                            result.data?.message ?: "An unexpected error occurred"
                        )
                    )
                    _uiState.value.copy(
                        isSuccess = result.data != null,
                        result = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "pick location: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: AddAddressEvent) {
        when (event) {
//            is AddAddressEvent.FirstNameChanged -> {
//                _uiState.value = _uiState.value.copy(name = event.name, nameError = null)
//            }
            is AddAddressEvent.FullAddressChanged -> {
                _uiState.value = _uiState.value.copy(fullAddress = event.address )
            }
//            is AddAddressEvent.LastNameChanged -> {
//                _uiState.value = _uiState.value.copy(lastName = event.lastName, lastNameError = null)
//            }
            is AddAddressEvent.PhoneChanged -> {
                _uiState.value = _uiState.value.copy(phone = event.phone, phoneError = null)
            }
            is AddAddressEvent.Skip -> {
                navigate(event.directions)
            }
            is AddAddressEvent.AreaChanged -> {
                _uiState.value = _uiState.value.copy(areaId = event.area, areaError = null)
                if(event.getCities)
                    getCities(event.area)
            }
            is AddAddressEvent.CityChanged -> {
                _uiState.value = _uiState.value.copy(cityId = event.city, cityError = null)
            }
            is AddAddressEvent.SaveAddress -> {
                submitData()
            }

            is AddAddressEvent.LatChanged -> {
                _uiState.value = _uiState.value.copy(latitude = event.lat)
            }
            is AddAddressEvent.LongChanged -> {
                _uiState.value = _uiState.value.copy(longitude = event.long)
            }
            is AddAddressEvent.InitState -> {
                _uiState.value = _uiState.value.copy(id = event.addressModel.id )
            }
            else -> {}
        }
    }

    private fun getAreas() {
        getAreasUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _uiState.value.copy(
                        isSuccessful = result.data != null,
                        resultAreas = result.data?.data
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

    private fun getCities(cityId:Int) {
        getCitiesUseCase(cityId).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _uiState.value.copy(
                        isSuccessful = result.data != null,
                        resultCities = result.data?.data
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

    private fun submitData() {
//        val firstResult = validateName(_uiState.value.name)
//        val lastResult = validateName(_uiState.value.lastName)
//        val phonedResult = validatePhone(_uiState.value.phone)

//        val hasError = listOf(
//            firstResult,
//            lastResult,
//            phonedResult,
//        ).any { !it.successful }

//        if (hasError) {
//            _uiState.value = _uiState.value.copy(
//                nameError = firstResult.errorMessage,
//                lastNameError = lastResult.errorMessage,
//                phoneError = phonedResult.errorMessage,
//            )
//            return
//        }
        saveAddress()
    }


    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }


}