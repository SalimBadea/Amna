package com.salem.amna.data.apiservice.add_product

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.add_product.StatusesResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface AddProductServices {

    @GET("brands")
    suspend fun getBrands(@Query("product_id") product_id: Int): Response<MainResponseModel<BrandsResponse>>

    @Multipart
    @POST("cart")
    suspend fun addItemToCart(
        @Query("item_id") itemId: Int,
        @Query("quantity") quantity: Int = 1,
        @Part images: MutableList<MultipartBody.Part>,
        @Query("brand_id") brand_id: Int,
        @Query("description") description: String,
        @Query("status_id") status_id: Int,
    ): Response<MainResponseModel<ContactUsResponse>>

    @GET("items_statuses")
    suspend fun getStatuses(): Response<MainResponseModel<StatusesResponse>>


}