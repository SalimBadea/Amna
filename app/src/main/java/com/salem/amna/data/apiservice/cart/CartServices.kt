package com.salem.amna.data.apiservice.cart

import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.addresses.GovernoratesResponse
import com.salem.amna.data.models.response.cart.CartResponse
import com.salem.amna.data.models.response.general.ContactUsResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface CartServices {

    @GET("cart")
    suspend fun getCart(): Response<MainResponseModel<CartResponse>>

    @Multipart
    @POST("checkout")
    suspend fun confirmCart(
        @Query("where_to_deliver") where_to_deliver: Int,
        @Query("user_address_id") user_address_id: Int?,
    ): Response<MainResponseModel<ContactUsResponse>>

    @POST("cart")
    suspend fun deleteItem(
        @Query("item_id") itemId: Int,
        @Query("_method") method: String = "DELETE"
    ): Response<MainResponseModel<ContactUsResponse>>
}