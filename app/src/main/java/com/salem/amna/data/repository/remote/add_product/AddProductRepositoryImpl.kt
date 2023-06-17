package com.salem.amna.data.repository.remote.add_product

import com.salem.amna.data.apiservice.add_product.AddProductServices
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.add_product.StatusesResponse
import com.salem.amna.domain.repository.add_product.AddProductRepository
import retrofit2.Response
import javax.inject.Inject

class AddProductRepositoryImpl @Inject constructor(
    private val api: AddProductServices
): AddProductRepository {

    override suspend fun getBrands(productId: Int): Response<MainResponseModel<BrandsResponse>> =
        api.getBrands(productId)

    override suspend fun getStatuses(): Response<MainResponseModel<StatusesResponse>> =
        api.getStatuses()
}