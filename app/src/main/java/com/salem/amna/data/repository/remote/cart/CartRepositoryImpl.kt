package net.moltaqa.talab_client.data.repository.remote.cart

import net.moltaqa.talab_client.data.apiservice.cart.CartService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.postbody.AddToCartBody
import net.moltaqa.talab_client.data.models.postbody.ApplyCouponBody
import net.moltaqa.talab_client.data.models.postbody.SendOrderCartBody
import net.moltaqa.talab_client.data.models.postbody.UpdateCartQuantityBody
import net.moltaqa.talab_client.data.models.response.cart.AddToCartResponse
import net.moltaqa.talab_client.data.models.response.cart.CartSummeryResponse
import net.moltaqa.talab_client.data.models.response.cart.ClearCartResponse
import net.moltaqa.talab_client.data.models.response.cart.UseWalletResponse
import net.moltaqa.talab_client.data.models.response.stores.StoresCategoriesResponse
import net.moltaqa.talab_client.domin.repository.cart.CartRepository
import retrofit2.Response
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val api: CartService
) : CartRepository {

    override suspend fun getCart(): Response<MainResponseModel<CartSummeryResponse>> = api.getCart()

    override suspend fun getCartInformation(): Response<MainResponseModel<CartSummeryResponse>> =
        api.getCartInformation()

    override suspend fun sendOrder(sendOrderCartBody: SendOrderCartBody): Response<MainResponseModel<CartSummeryResponse>> =
        api.sendOrder(sendOrderCartBody)

    override suspend fun addToCart(addToCartBody: AddToCartBody): Response<MainResponseModel<AddToCartResponse>> =
        api.addToCart(addToCartBody)

    override suspend fun updateItemCartCount(updateCartQuantityBody: UpdateCartQuantityBody):
            Response<MainResponseModel<CartSummeryResponse>> =
        api.updateItemCartCount(updateCartQuantityBody)

    override suspend fun removeFromCart(productId: String): Response<MainResponseModel<CartSummeryResponse>> =
        api.removeFromCart(productId)

    override suspend fun useWalletPoints(): Response<MainResponseModel<UseWalletResponse>>
        = api.useWalletPoints()



    override suspend fun clearCart(): Response<MainResponseModel<ClearCartResponse>> =
        api.clearCart()

    override suspend fun applyCoupon(applyCouponBody: ApplyCouponBody): Response<MainResponseModel<CartSummeryResponse>> =
        api.applyCoupon(applyCouponBody)



}