package net.moltaqa.talab_client.data.repository.remote.favorite

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.auth.AuthService
import net.moltaqa.talab_client.data.apiservice.favorite.FavoriteService
import net.moltaqa.talab_client.data.apiservice.home.HomeService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.response.auth.LoginResponse
import net.moltaqa.talab_client.data.models.postbody.LoginBody
import net.moltaqa.talab_client.data.models.postbody.ToggleFavoriteBody
import net.moltaqa.talab_client.data.models.response.favorite.FavoriteProduct
import net.moltaqa.talab_client.data.models.response.favorite.FavoritesResponse
import net.moltaqa.talab_client.data.models.response.favorite.ToggleFavoriteResponse
import net.moltaqa.talab_client.data.models.response.home.HomeResponse
import net.moltaqa.talab_client.data.pagingsource.FavoriteProductsPagingSource
import net.moltaqa.talab_client.domin.repository.auth.LoginRepository
import net.moltaqa.talab_client.domin.repository.favorite.FavoriteRepository
import net.moltaqa.talab_client.domin.repository.home.HomeRepository
import net.moltaqa.talab_client.util.Constants.NETWORK_PAGE_SIZE
import retrofit2.Response
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val api: FavoriteService
) : FavoriteRepository {

    private val invalidatingFactory = InvalidatingPagingSourceFactory {
        FavoriteProductsPagingSource(api)
    }

    override fun getFavoriteProducts(): Flow<PagingData<FavoriteProduct>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {FavoriteProductsPagingSource(api)}
        ).flow
    }


    override suspend fun toggleFavoriteProduct(toggleFavoriteBody: ToggleFavoriteBody):
            Response<MainResponseModel<ToggleFavoriteResponse>> {
        //invalidatingFactory.invalidate()
        return api.toggleFavoriteProduct(toggleFavoriteBody)
    }


}