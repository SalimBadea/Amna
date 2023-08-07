package com.salem.amna.presentation.ui.my_account.exchange_points

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import com.salem.amna.data.models.post_body.WithdrawalToBankAccountBody
import com.salem.amna.data.models.post_body.WithdrawalToBankCardBody
import com.salem.amna.data.models.post_body.WithdrawalToWalletBody
import com.salem.amna.domain.use_case.auth.validation.ValidateMessage
import com.salem.amna.domain.use_case.auth.validation.ValidateName
import com.salem.amna.domain.use_case.auth.validation.ValidatePhone
import com.salem.amna.domain.use_case.general.ContactUsUseCase
import com.salem.amna.domain.use_case.points.*
import com.salem.amna.presentation.common.NavigationCommand
import com.salem.amna.presentation.common.UiEffect
import com.salem.amna.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ExchangePointsViewModel @Inject constructor(
    private val getBanksUseCase: GetBanksUseCase,
    private val getPointsUseCase: GetPointsUseCase,
    private val getWithdrawalsUseCase: GetWithdrawalsUseCase,
    private val withdrawalToBankAccountUseCase: WithdrawalToBankAccountUseCase,
    private val withdrawalToBankCardUseCase: WithdrawalToBankCardUseCase,
    private val withdrawalToWalletUseCase: WithdrawalToWalletUseCase,
    ) : ViewModel() {
    private val TAG = "ContactUsViewModel"
    private var _uiState: MutableStateFlow<ExchangePointsState> = MutableStateFlow(ExchangePointsState())
    val uiState = _uiState.asStateFlow()

    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
    val navigation = _navigation.asStateFlow()

    private val _effect: Channel<UiEffect> = Channel()
    val effect = _effect.receiveAsFlow()


    private fun getBanks() {
        getBanksUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    ExchangePointsState(
                        isBanks = result.data != null,
                        isLoading = false,
                        banksResult = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "ContactUs: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isBanks = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isBanks = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getPoints(){
        getPointsUseCase().onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    ExchangePointsState(
                        isPoints = result.data != null,
                        isLoading = false,
                        pointsResult = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "ContactUs: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isPoints = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isPoints = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getWithdrawals(type: String?){
        getWithdrawalsUseCase.invoke(type).onEach { result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    ExchangePointsState(
                        isWithdrawals = result.data != null,
                        isLoading = false,
                        withdrawalsResult = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "ContactUs: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isWithdrawals = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isWithdrawals = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun withdrawBankAccount(body: WithdrawalToBankAccountBody){
        withdrawalToBankAccountUseCase.invoke(body).onEach {result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    ExchangePointsState(
                        isWithdrawAccount = result.data != null,
                        isLoading = false,
                        message = result.data?.message!!,
                        withdrawBankAccountResult = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "ContactUs: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isWithdrawals = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isWithdrawals = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun withdrawBankCard(body: WithdrawalToBankCardBody){
        withdrawalToBankCardUseCase.invoke(body).onEach {result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    ExchangePointsState(
                        isWithdrawCard = result.data != null,
                        isLoading = false,
                        message = result.data?.message!!,
                        withdrawBankAccountResult = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "ContactUs: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isWithdrawals = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isWithdrawals = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun withdrawWallet(body: WithdrawalToWalletBody){
        withdrawalToWalletUseCase.invoke(body).onEach {result ->
            _uiState.value = when (result) {
                is Resource.Success -> {
                    _effect.send(UiEffect.ShowToast(result.data?.message?:"An unexpected error occurred"))
                    ExchangePointsState(
                        isWithdrawWallet = result.data != null,
                        isLoading = false,
                        message = result.data?.message!!,
                        withdrawBankAccountResult = result.data?.data
                    )
                }
                is Resource.Error -> {
                    val message = result.message ?: "An unexpected error occurred"
                    Log.e(TAG, "ContactUs: error message $message")
                    _effect.send(UiEffect.ShowToast(message))
                    _uiState.value.copy(error = message, isLoading = false, isWithdrawals = false)
                }
                is Resource.Loading -> {
                    _uiState.value.copy(isLoading = true, error = "", isWithdrawals = false)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: ExchangePointsEvent) {
        when (event) {
            is ExchangePointsEvent.LoadBanks -> {
                getBanks()
            }
            is ExchangePointsEvent.GetPoints -> {
                getPoints()
            }
            is ExchangePointsEvent.GetWithdrawals -> {
                getWithdrawals(event.type)
            }
            is ExchangePointsEvent.WithdrawToBankAccount -> {
                withdrawBankAccount(event.body)
            }
            is ExchangePointsEvent.WithdrawToBankCard -> {
                withdrawBankCard(event.body)
            }
            is ExchangePointsEvent.WithdrawToWallet -> {
                withdrawWallet(event.body)
            }
        }
    }

    fun navigate(navDirections: NavDirections) {
        _navigation.value = NavigationCommand.ToDirection(navDirections)
    }

}