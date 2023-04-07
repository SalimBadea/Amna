package net.moltaqa.talab_client.data.repository.remote.stores

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.stores.StoresService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.common.ProductModel
import net.moltaqa.talab_client.data.models.response.stores.StoreDetailsResponse
import net.moltaqa.talab_client.data.models.response.stores.StoreRate
import net.moltaqa.talab_client.data.models.response.stores.StoresCategoriesResponse
import net.moltaqa.talab_client.data.pagingsource.ProductsStoreCategoryPagingSource
import net.moltaqa.talab_client.data.pagingsource.StoreRatsPagingSource
import net.moltaqa.talab_client.domin.repository.stores.StoresRepository
import net.moltaqa.talab_client.util.Constants
import retrofit2.Response
import javax.inject.Inject

class StoresRepositoryImpl @Inject constructor(
    private val api: StoresService
) : StoresRepository {

    override suspend fun storesByCategoryId(
        categoryId: Int,
        map: Map<String, String>
    ): Response<MainResponseModel<StoresCategoriesResponse>> =
        api.storesByCategoryId(categoryId, map)


    override suspend fun storeDetails(
        storeId: Int,
        map: Map<String, String>
    ): Response<MainResponseModel<StoreDetailsResponse>> = api.storeDetails(storeId, map)

    override fun storeDetailsRating(
        storeId: Int,
    ): Flow<PagingData<StoreRate>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { StoreRatsPagingSource(storeId, api) }
        ).flow
    }

    override fun storeProductsByCategory(
        storeId: Int,
        categoryId: Int
    ): Flow<PagingData<ProductModel>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ProductsStoreCategoryPagingSource(storeId, categoryId, api) }
        ).flow

}