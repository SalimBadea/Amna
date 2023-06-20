package com.salem.amna.domain.use_case.add_product

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.domain.repository.add_product.AddProductRepository
import com.salem.amna.domain.repository.addresses.AddressesRepository
import com.salem.amna.domain.repository.cart.CartRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class AddItemUseCase @Inject constructor(
    private val repository: AddProductRepository
) {
    private val TAG = "AddItemUseCase"
    operator fun invoke(itemId: Int, qty: Int, images: MutableList<MultipartBody.Part>, brandId: Int, description: String, statusId: Int): Flow<Resource<MainResponseModel<ContactUsResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val myResponse = repository.addItemToCart(itemId, qty, images, brandId, description, statusId)
            Log.d(TAG, "invoke: AddItem use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: AddItem use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error AddItem use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception AddItem use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage?.toString() ?: ""
                )
            )
        }
    }
}
