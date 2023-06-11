package com.salem.amna.domain.use_case.addresses

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.AddressesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.domain.repository.addresses.AddressesRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    val repository: AddressesRepository
) {

    private val TAG = "GetAddressesUseCase"

    operator fun invoke():Flow<Resource<MainResponseModel<AddressesResponse>>> = flow {
        try {
            emit(Resource.Loading())

            val myResponse = repository.getAddresses()
            Log.d(TAG, "invoke: GetAddresses use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: GetAddresses use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader()
                    .use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error GetAddresses use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        }catch (e: Exception) {
            Log.e(TAG, "invoke: Exception GetAddresses use case ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }
}