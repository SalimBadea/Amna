package com.salem.amna.presentation.ui.add_product

import androidx.navigation.NavDirections
import com.salem.amna.data.models.common.AddressModel
import com.salem.amna.presentation.ui.cart.CartEvent
import okhttp3.MultipartBody


sealed class AddProductEvent {

    data class BrandChanged(val brandId: Int) : AddProductEvent()
    data class StatusesChanged(val statusId: Int) : AddProductEvent()
    data class AddItem(
        val itemId: Int,
        val qty: Int,
        val images: MutableList<MultipartBody.Part>,
        val brandId: Int,
        val description: String,
        val statusId: Int
    ) : AddProductEvent()
    object SaveProduct : AddProductEvent()
}