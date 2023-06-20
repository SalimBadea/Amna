package com.salem.amna.domain.repository.add_product

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.add_product.StatusesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface AddProductRepository {

    suspend fun getBrands(productId: Int): Response<MainResponseModel<BrandsResponse>>

    suspend fun addItemToCart(itemId: Int, qty: Int, images: MutableList<MultipartBody.Part>, brandId: Int, description: String, statusId: Int): Response<MainResponseModel<ContactUsResponse>>

    suspend fun getStatuses(): Response<MainResponseModel<StatusesResponse>>
}