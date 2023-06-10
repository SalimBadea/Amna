package com.salem.amna.domain.repository.categories

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse
import retrofit2.Response

interface CategoriesRepository {

    suspend fun getCategories(): Response<MainResponseModel<CategoriesResponse>>

    suspend fun getCategoryItems(categoryId: Int): Response<MainResponseModel<CategoryItemsResponse>>
}