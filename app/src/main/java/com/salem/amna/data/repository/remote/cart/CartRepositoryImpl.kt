package com.salem.amna.data.repository.remote.cart

import com.salem.amna.data.apiservice.cart.CartServices
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.cart.CartResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.domain.repository.cart.CartRepository
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val api: CartServices
): CartRepository{

    override suspend fun getCart(): Response<MainResponseModel<CartResponse>> =
        api.getCart()

    override suspend fun deleteItem(
        itemId: Int,
        method: String
    ): Response<MainResponseModel<ContactUsResponse>> =
        api.deleteItem(itemId, method)
}