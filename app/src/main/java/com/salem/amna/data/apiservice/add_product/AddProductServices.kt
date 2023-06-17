package com.salem.amna.data.apiservice.add_product

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.add_product.StatusesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AddProductServices {

    @GET("brands")
    suspend fun getBrands(@Query("product_id") product_id: Int): Response<MainResponseModel<BrandsResponse>>

    @GET("items_statuses")
    suspend fun getStatuses(): Response<MainResponseModel<StatusesResponse>>


}