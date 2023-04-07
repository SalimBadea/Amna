package net.moltaqa.talab_client.data.repository.remote.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.search.SearchService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.common.ProductModel
import net.moltaqa.talab_client.data.models.common.StoreModel
import net.moltaqa.talab_client.data.models.response.search.SearchProductsResponse
import net.moltaqa.talab_client.data.models.response.search.SearchStoreResponse
import net.moltaqa.talab_client.data.pagingsource.ProductsStoreCategoryPagingSource
import net.moltaqa.talab_client.data.pagingsource.SearchProductPagingSource
import net.moltaqa.talab_client.data.pagingsource.SearchStorePagingSource
import net.moltaqa.talab_client.domin.repository.search.SearchRepository
import net.moltaqa.talab_client.util.Constants
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: SearchService
) : SearchRepository {

    override fun getStoreSearchData(query: String):
            Flow<PagingData<StoreModel>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchStorePagingSource(query, api) }
        ).flow

    override fun getProductSearchData(query: String):
            Flow<PagingData<ProductModel>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SearchProductPagingSource(query, api) }
        ).flow


}