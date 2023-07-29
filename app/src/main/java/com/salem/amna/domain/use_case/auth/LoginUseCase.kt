package com.salem.amna.domain.use_case.auth

import android.util.Log
import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import com.salem.amna.domain.repository.auth.LoginRepository
import com.salem.amna.util.Constants
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    private val TAG = "LoginUseCase"
    operator fun invoke(loginBody: LoginBody): Flow<Resource<MainResponseModel<LoginResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val loginResponse = repository.login(loginBody)
            Log.d(TAG, "invoke: Login use case ${loginResponse.isSuccessful}")
            if (loginResponse.isSuccessful && loginResponse.body() != null) {
                loginResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: Login use case errorBody ${loginResponse.errorBody()}")
                val errorString = loginResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8

                var errorMessage : String = ""
                if (getErrorResponse(errorString!!).errors!!.isEmpty())
                    errorMessage =
                        getErrorResponse(errorString).message ?: ""
                else
                    errorMessage =
                        getErrorResponse(errorString).errors!![0] ?: ""
                Log.e(TAG, "invoke: Error Login use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception Login use case ${e.localizedMessage}")

            emit(
                Resource.Error(
                    e.localizedMessage?:""
                )
            )
        }
    }
}
