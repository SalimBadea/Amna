//package com.salem.amna.presentation.ui.auth.verification_code
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavDirections
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.*
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.withContext
//import net.moltaqa.talab_client.data.repository.local.preference.LocalePreference
//import net.moltaqa.talab_client.domin.use_case.auth.RegisterUseCase
//import net.moltaqa.talab_client.domin.use_case.auth.SubmitRegisterUseCase
//import net.moltaqa.talab_client.presentation.common.NavigationCommand
//import net.moltaqa.talab_client.presentation.common.UiEffect
//import net.moltaqa.talab_client.presentation.ui.auth.forget_password.forgetverificationcode.ForgetVerificationCodeEvent
//import net.moltaqa.talab_client.util.Resource
//import javax.inject.Inject
//
//@HiltViewModel
//class VerificationCodeViewModel @Inject constructor(
//    private val submitRegisterUseCase: SubmitRegisterUseCase,
//    private val registerUseCase: RegisterUseCase,
//    private val localePreference: LocalePreference,
//) : ViewModel() {
//    private val TAG = "VerificationCodeViewMod"
//    private var _uiState: MutableStateFlow<VerificationCodeState> =
//        MutableStateFlow(VerificationCodeState())
//    val uiState = _uiState.asStateFlow()
//
//    private var _navigation: MutableStateFlow<NavigationCommand?> = MutableStateFlow(null)
//    val navigation = _navigation.asStateFlow()
//
//    private val _effect: Channel<UiEffect> = Channel()
//    val effect = _effect.receiveAsFlow()
//    private var fcmToken: String? = null
//
//    init {
//        viewModelScope.launch {
//            fcmToken = localePreference.getFCMToken()
//        }
//    }
//
//    private fun submitRegister(code: String) {
//        submitRegisterUseCase( _uiState.value.copy(code = code).toSubmitRegisterBody().copy(fcmToken = fcmToken)).onEach { result ->
//            _uiState.value = when (result) {
//                is Resource.Success -> {
//                    saveToken(result.data?.data?.accessToken ?: "")
//
//                    _effect.send(UiEffect.ShowToast(result.data?.message ?: ""))
//
//                    _uiState.value.copy(isSuccess = result.data != null, isUpdate = false, isLoading = false)
//                }
//                is Resource.Error -> {
//                    val message = result.message ?: "An unexpected error occurred"
//                    Log.e(TAG, "register: error message $message")
//                    _effect.send(UiEffect.ShowToast(message))
//                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
//                }
//                is Resource.Loading -> {
//                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
//    fun resendRegisterCode() {
//        registerUseCase( _uiState.value.toRegisterBody()).onEach { result ->
//            _uiState.value = when (result) {
//                is Resource.Success -> {
//                    _effect.send(UiEffect.ShowToast(result.data?.message ?: ""))
//
//                    _uiState.value.copy(isUpdate = result.data != null, result = result.data?.data , isLoading = false)
//                }
//                is Resource.Error -> {
//                    val message = result.message ?: "An unexpected error occurred"
//                    Log.e(TAG, "register: error message $message")
//                    _effect.send(UiEffect.ShowToast(message))
//                    _uiState.value.copy(error = message, isLoading = false, isSuccess = false)
//                }
//                is Resource.Loading -> {
//                    _uiState.value.copy(isLoading = true, error = "", isSuccess = false)
//                }
//            }
//        }.launchIn(viewModelScope)
//    }
//
//
//    fun onEvent(event: VerificationCodeEvent) {
//        when (event) {
//           is VerificationCodeEvent.ResendCode -> {
//
//            }
//            is VerificationCodeEvent.Confirm -> {
//                submitData(event.code)
//            }
//            is VerificationCodeEvent.InitRegisterData -> {
//                _uiState.value = _uiState.value.copy(
//                    email = event.state.email,
//                    name = event.state.name,
//                    phone = event.state.phone,
//                    password = event.state.password,
//                    passwordConfirmation = event.state.passwordConfirmation,
//                    gender = event.state.gender,
//                    birthDate = event.state.birthDate,
//                )
//            }
//        }
//    }
//
//    fun navigate(navDirections: NavDirections) {
//        _navigation.value = NavigationCommand.ToDirection(navDirections)
//        _navigation.value = NavigationCommand.Stop
//
//    }
//
//    private fun submitData(code:String) {
//
//        submitRegister(code)
//    }
//
//    private fun saveToken(userId: String) {
//        runBlocking{
//
//            localePreference.setLoginState(true)
//            localePreference.saveToken(userId)
//        }
//    }
//
//
//}