package com.salem.amna.domain.use_case.home

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.home.HomeResponse
import com.salem.amna.domain.repository.home.HomeRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    private val TAG = "GetHomeUseCase"

    operator fun invoke(status: Int?):Flow<Resource<MainResponseModel<HomeResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val myResponse = repository.getHome(status)
            Log.d(TAG, "invoke: GetHome use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: GetHome use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader()
                    .use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error GetHome use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception GetHome use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage ?: ""
                )
            )
        }
    }
}