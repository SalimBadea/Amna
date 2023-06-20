package com.salem.amna.presentation.ui.home

import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse
import com.salem.amna.data.models.response.home.HomeResponse

data class HomeState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = "",
    val result: HomeResponse? = null,
)