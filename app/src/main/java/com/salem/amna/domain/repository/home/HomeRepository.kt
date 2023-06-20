package com.salem.amna.domain.repository.home

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.home.HomeResponse
import retrofit2.Response

interface HomeRepository {

    suspend fun getHome(status: Int?): Response<MainResponseModel<HomeResponse>>
}