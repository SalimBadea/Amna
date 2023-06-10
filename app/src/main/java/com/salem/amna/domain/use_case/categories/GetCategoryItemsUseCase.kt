package com.salem.amna.domain.use_case.categories

import android.util.Log
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse
import com.salem.amna.domain.repository.categories.CategoriesRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCategoryItemsUseCase @Inject constructor(
    val repository: CategoriesRepository
) {

    private val TAG = "GetCategoryItemsUseCase"

    operator fun invoke(categoryId: Int): Flow<Resource<MainResponseModel<CategoryItemsResponse>>> = flow {

        try {
            emit(Resource.Loading())
            val myResponse = repository.getCategoryItems(categoryId)
            Log.d(TAG, "invoke: GetCategoryItems use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: GetCategoryItems use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader()
                    .use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error GetCategoryItems use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception GetCategoryItems use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage ?: ""
                )
            )
        }
    }
}