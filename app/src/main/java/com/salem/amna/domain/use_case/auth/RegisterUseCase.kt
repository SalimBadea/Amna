package com.salem.amna.domain.use_case.auth

import android.util.Log
import com.salem.amna.data.models.post_body.RegisterBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.RegisterResponse
import com.salem.amna.domain.repository.auth.RegisterRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: RegisterRepository
) {
    private val TAG = "RegisterUseCase"
    operator fun invoke(registerBody: RegisterBody): Flow<Resource<MainResponseModel<RegisterResponse?>>> = flow {
        try {
            emit(Resource.Loading())
            val registerResponse = repository.register(registerBody)
            if (registerResponse.isSuccessful && registerResponse.body() != null) {
                registerResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                val errorString = registerResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8
                val errorMessage =
                    getErrorResponse(errorString!!).errors!![0] ?: ""
                Log.e(TAG, "invoke: Error Register use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception Register use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage?:""
                )
            )
        }
    }
}