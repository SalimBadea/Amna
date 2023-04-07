package net.moltaqa.talab_client.data.repository.remote.home

import net.moltaqa.talab_client.data.apiservice.auth.AuthService
import net.moltaqa.talab_client.data.apiservice.home.HomeService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.response.auth.LoginResponse
import net.moltaqa.talab_client.data.models.postbody.LoginBody
import net.moltaqa.talab_client.data.models.response.home.HomeResponse
import net.moltaqa.talab_client.domin.repository.auth.LoginRepository
import net.moltaqa.talab_client.domin.repository.home.HomeRepository
import retrofit2.Response
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val api: HomeService
) : HomeRepository {
    override suspend fun home(map: Map<String, String>): Response<MainResponseModel<HomeResponse>> {
        return api.home(map)
    }


}