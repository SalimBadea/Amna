package com.salem.amna.domain.repository.add_product

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.add_product.StatusesResponse
import retrofit2.Response

interface AddProductRepository {

    suspend fun getBrands(productId: Int): Response<MainResponseModel<BrandsResponse>>

    suspend fun getStatuses(): Response<MainResponseModel<StatusesResponse>>
}