package com.salem.amna.domain.repository.courses

import com.salem.amna.data.models.common.CoursesModel
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse
import com.salem.amna.data.models.response.courses.CoursesResponse
import retrofit2.Response

interface CoursesRepository {

    suspend fun getCourses(): Response<MainResponseModel<CoursesResponse>>

}