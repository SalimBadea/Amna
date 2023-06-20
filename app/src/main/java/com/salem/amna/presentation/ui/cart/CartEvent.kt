package com.salem.amna.presentation.ui.cart

import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.presentation.ui.my_account.addresses.AddressEvent
import okhttp3.MultipartBody

sealed class CartEvent {

    object LoadCart : CartEvent()
    data class DeleteItem(val itemId: Int) : CartEvent()
    data class Checkout(
        val where_to_deliver: Int, val user_address_id: Int?
    ) : CartEvent()
}