package com.salem.amna.presentation.ui.add_product

import androidx.navigation.NavDirections
import com.salem.amna.data.models.common.AddressModel


sealed class AddProductEvent {

    data class BrandChanged(val brandId: Int) : AddProductEvent()
    data class StatusesChanged(val statusId: Int) : AddProductEvent()
    object SaveProduct : AddProductEvent()
}