package com.salem.amna.presentation.ui.categories

import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse

data class CategoriesState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isSubCategory: Boolean = false,
    val error: String = "",
    val result: CategoriesResponse? = null,
    val categoryItemsResult: CategoryItemsResponse? = null
)