package com.salem.amna.data.apiservice.categories

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CategoriesService {

    @GET("categories")
    suspend fun getCategories(): Response<MainResponseModel<CategoriesResponse>>

    @GET("items")
    suspend fun getCategoryItems(
        @Query("category_id") categoryId: Int
    ): Response<MainResponseModel<CategoryItemsResponse>>
}