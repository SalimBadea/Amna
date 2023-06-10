package com.salem.amna.domain.use_case.auth

import android.util.Log
import com.salem.amna.data.models.post_body.LoginBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.auth.LoginResponse
import com.salem.amna.domain.repository.auth.LoginRepository
import com.salem.amna.domain.repository.auth.VerifyRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VerifyUseCase @Inject constructor(
    private val repository: VerifyRepository
) {
    private val TAG = "LoginUseCase"
    operator fun invoke(code:String): Flow<Resource<MainResponseModel<Any>>> = flow {
        try {
            emit(Resource.Loading())
            val loginResponse = repository.verify(code)
            Log.d(TAG, "invoke: Login use case ${loginResponse.isSuccessful}")
            if (loginResponse.isSuccessful && loginResponse.body() != null) {
                loginResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: Login use case errorBody ${loginResponse.errorBody()}")
                val errorString = loginResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).errors!![0] ?: ""
                Log.e(TAG, "invoke: Error Login use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception Login use case ${e.localizedMessage}")
//            val errorMessage =
//                Constants.getErrorResponse(loginResponse.errorBody().toString()).message ?: ""
//            Log.e(TAG, "invoke: Error Login use case $errorMessage")
//            emit(Resource.Error(errorMessage))
            emit(
                Resource.Error(
                    e.localizedMessage?:""
                )
            )
        }
    }
}
