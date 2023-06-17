package com.salem.amna.domain.repository.cart

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.cart.CartResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import retrofit2.Response

interface CartRepository {

    suspend fun getCart(): Response<MainResponseModel<CartResponse>>

    suspend fun deleteItem(itemId: Int, method: String): Response<MainResponseModel<ContactUsResponse>>
}