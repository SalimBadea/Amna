package com.salem.amna.presentation.ui.cart

import com.salem.amna.data.models.response.cart.CartResponse
import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.data.models.response.categories.CategoryItemsResponse

data class CartState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String = "",
    val result: CartResponse? = null,
)