package com.salem.amna.data.repository.remote.add_product

import com.salem.amna.data.apiservice.add_product.AddProductServices
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.add_product.StatusesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.domain.repository.add_product.AddProductRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class AddProductRepositoryImpl @Inject constructor(
    private val api: AddProductServices
): AddProductRepository {

    override suspend fun getBrands(productId: Int): Response<MainResponseModel<BrandsResponse>> =
        api.getBrands(productId)

    override suspend fun addItemToCart(
        itemId: Int,
        qty: Int,
        images: MutableList<MultipartBody.Part>,
        brandId: Int,
        description: String,
        statusId: Int
    ): Response<MainResponseModel<ContactUsResponse>> = api.addItemToCart(itemId, qty, images, brandId, description, statusId)

    override suspend fun getStatuses(): Response<MainResponseModel<StatusesResponse>> =
        api.getStatuses()
}