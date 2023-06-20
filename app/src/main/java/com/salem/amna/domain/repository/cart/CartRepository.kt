package com.salem.amna.domain.repository.cart

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.cart.CartResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import okhttp3.MultipartBody
import retrofit2.Response

interface CartRepository {

    suspend fun getCart(): Response<MainResponseModel<CartResponse>>

    suspend fun confirmCart(where_to_deliver: Int, user_address_id: Int?): Response<MainResponseModel<ContactUsResponse>>

    suspend fun deleteItem(itemId: Int, method: String): Response<MainResponseModel<ContactUsResponse>>
}