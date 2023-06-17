package com.salem.amna.presentation.ui.cart

import com.salem.amna.data.models.response.categories.CategoriesResponse
import com.salem.amna.presentation.ui.my_account.addresses.AddressEvent

sealed class CartEvent {

    object LoadCart : CartEvent()
    data class DeleteItem(val itemId: Int) : CartEvent()
}