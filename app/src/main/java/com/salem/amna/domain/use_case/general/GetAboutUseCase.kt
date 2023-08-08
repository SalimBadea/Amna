package com.salem.amna.domain.use_case.general

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.GeneralResponse
import com.salem.amna.data.models.response.pages.PagesResponse
import com.salem.amna.domain.repository.general.GeneralRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAboutUseCase @Inject constructor(
    private val repository: GeneralRepository
) {
    private val TAG = "GeneralUseCase"
    operator fun invoke(): Flow<Resource<MainResponseModel<PagesResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val myResponse = repository.getAbout()
            Log.d(TAG, "invoke: General use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: General use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error General use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception General use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage?:""
                )
            )
        }
    }
}
