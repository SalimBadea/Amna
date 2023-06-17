package com.salem.amna.presentation.ui.add_product

import com.salem.amna.data.models.response.add_product.BrandsResponse
import com.salem.amna.data.models.response.add_product.StatusesResponse

data class AddProductState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isSuccessful: Boolean = false,
    val error: String = "",
    val resultBrands: BrandsResponse? = null,
    val resultStatuses: StatusesResponse? = null,

    val brandId: Int? = null,
    val brandError: Int? = null,
    val statusId: Int? = null,
    val statusError: Int? = null,
)