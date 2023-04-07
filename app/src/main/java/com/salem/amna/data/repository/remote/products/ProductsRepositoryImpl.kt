package net.moltaqa.talab_client.data.repository.remote.products

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.product.ProductService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.common.ProductModel
import net.moltaqa.talab_client.data.models.response.product.ProductDetailsResponse
import net.moltaqa.talab_client.data.pagingsource.OffersProductsPagingSource
import net.moltaqa.talab_client.domin.repository.products.ProductsRepository
import net.moltaqa.talab_client.util.Constants
import retrofit2.Response
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val api: ProductService
) : ProductsRepository {


    override suspend fun productDetails(productId: Int): Response<MainResponseModel<ProductDetailsResponse>> =
        api.productDetails(productId)

    override suspend fun productDetailsVariations(
        productId: Int,
        variations: List<String>,
        variationParents: List<String>,
        lastProduct: List<String>
    ): Response<MainResponseModel<ProductDetailsResponse>> =
        api.productDetailsVariations(productId, variations,variationParents, lastProduct)


    override fun offersProducts(): Flow<PagingData<ProductModel>> =
        Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { OffersProductsPagingSource(api) }
        ).flow


}