package com.salem.amna.data.apiservice.courses

import com.salem.amna.data.models.common.CoursesModel
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.courses.CoursesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoursesService {

    @GET("courses")
    suspend fun getCourses(): Response<MainResponseModel<CoursesResponse>>

}