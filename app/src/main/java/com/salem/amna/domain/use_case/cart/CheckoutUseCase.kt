package com.salem.amna.domain.use_case.cart

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.domain.repository.addresses.AddressesRepository
import com.salem.amna.domain.repository.cart.CartRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CheckoutUseCase @Inject constructor(
    private val repository: CartRepository
) {
    private val TAG = "CheckoutUseCase"
    operator fun invoke(where_to_deliver: Int, user_address_id: Int?): Flow<Resource<MainResponseModel<ContactUsResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val myResponse = repository.confirmCart(where_to_deliver, user_address_id)
            Log.d(TAG, "invoke: Checkout use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: Checkout use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error Checkout use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception Checkout use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage?.toString() ?: ""
                )
            )
        }
    }
}
