package com.salem.amna.data.repository.remote.courses

import com.salem.amna.data.apiservice.courses.CoursesService
import com.salem.amna.data.models.common.CoursesModel
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.courses.CoursesResponse
import com.salem.amna.domain.repository.courses.CoursesRepository
import retrofit2.Response
import javax.inject.Inject

class CoursesRepositoryImpl @Inject constructor(
    private val api: CoursesService
): CoursesRepository {
    override suspend fun getCourses(): Response<MainResponseModel<CoursesResponse>> {
        return api.getCourses()
    }
}