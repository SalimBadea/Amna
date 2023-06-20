package com.salem.amna.data.repository.remote

import com.salem.amna.data.apiservice.home.HomeServices
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.home.HomeResponse
import com.salem.amna.domain.repository.home.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeServices
): HomeRepository {

    override suspend fun getHome(status: Int?): Response<MainResponseModel<HomeResponse>> =
        api.getHome(status)
}