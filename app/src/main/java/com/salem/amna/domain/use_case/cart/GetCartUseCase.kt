package com.salem.amna.domain.use_case.cart

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.cart.CartResponse
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.domain.repository.cart.CartRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val repository: CartRepository
) {

    private val TAG = "GetCartUseCase"

    operator fun invoke(): Flow<Resource<MainResponseModel<CartResponse>>> = flow {

        try {
            emit(Resource.Loading())
            val myResponse = repository.getCart()
            Log.d(TAG, "invoke: GetCart use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: GetCart use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader()
                    .use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error GetCart use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception GetCart use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage ?: ""
                )
            )
        }
    }

}