package com.salem.amna.presentation.ui.categories

import com.salem.amna.data.models.response.categories.CategoriesResponse

sealed class CategoriesEvent {

    object LoadCategories : CategoriesEvent()
    data class GetCategoryItems(val categoryId: Int) : CategoriesEvent()
}