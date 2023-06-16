package com.salem.amna.domain.use_case.addresses

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.domain.repository.addresses.AddressesRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAddressUseCase @Inject constructor(
    private val repository: AddressesRepository
) {
    private val TAG = "DeleteAddressUseCase"
    operator fun invoke(addressId:Int): Flow<Resource<MainResponseModel<ContactUsResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val myResponse = repository.deleteAddress(addressId, "DELETE")
            Log.d(TAG, "invoke: DeleteAddress use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: DeleteAddress use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error DeleteAddress use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception DeleteAddress use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage?.toString() ?: ""
                )
            )
        }
    }
}
