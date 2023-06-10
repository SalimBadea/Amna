package com.salem.amna.data.repository.remote.categories

import com.salem.amna.data.apiservice.categories.CategoriesService
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse
import com.salem.amna.domain.repository.categories.CategoriesRepository
import retrofit2.Response
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    val api: CategoriesService
): CategoriesRepository {

    override suspend fun getCategories(): Response<MainResponseModel<CategoriesResponse>> =
        api.getCategories()

    override suspend fun getCategoryItems(categoryId: Int): Response<MainResponseModel<CategoryItemsResponse>> =
        api.getCategoryItems(categoryId)

}