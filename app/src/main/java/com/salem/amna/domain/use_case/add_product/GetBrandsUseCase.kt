package com.salem.amna.domain.use_case.add_product

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.addresses.CitiesResponse
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.domain.repository.add_product.AddProductRepository
import com.salem.amna.domain.repository.addresses.AddressesRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class GetBrandsUseCase @Inject constructor(
    val repository: AddProductRepository
) {

    private val TAG = "GetBrandsUseCase"

    operator fun invoke(productId: Int):Flow<Resource<MainResponseModel<BrandsResponse>>> = flow {
        try {
            emit(Resource.Loading())

            val myResponse = repository.getBrands(productId)
            Log.d(TAG, "invoke: GetBrands use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: GetBrands use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader()
                    .use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error GetBrands use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        }catch (e: Exception) {
            Log.e(TAG, "invoke: Exception GetBrands use case ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }
}