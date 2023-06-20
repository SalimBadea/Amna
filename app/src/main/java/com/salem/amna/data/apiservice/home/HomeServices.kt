package com.salem.amna.data.apiservice.home

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.home.HomeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeServices {

    @GET("home")
    suspend fun getHome(@Query("status") status: Int?): Response<MainResponseModel<HomeResponse>>
}