package com.salem.amna.presentation.ui.courses

import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse
import com.salem.amna.data.models.response.courses.CoursesResponse

data class CoursesState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = "",
    val result: CoursesResponse? = null,
)