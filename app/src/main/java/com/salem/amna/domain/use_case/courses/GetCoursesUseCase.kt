package com.salem.amna.domain.use_case.courses

import android.util.Log
import com.salem.amna.data.models.common.CoursesModel
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.courses.CoursesResponse
import com.salem.amna.domain.repository.categories.CategoriesRepository
import com.salem.amna.domain.repository.courses.CoursesRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    val repository: CoursesRepository
) {

    private val TAG = "GetCoursesUseCase"

    operator fun invoke(): Flow<Resource<MainResponseModel<CoursesResponse>>> = flow {

        try {
            emit(Resource.Loading())
            val myResponse = repository.getCourses()
            Log.d(TAG, "invoke: GetCourses use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: GetCourses use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader()
                    .use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error GetCourses use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception GetCourses use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage ?: ""
                )
            )
        }
    }
}