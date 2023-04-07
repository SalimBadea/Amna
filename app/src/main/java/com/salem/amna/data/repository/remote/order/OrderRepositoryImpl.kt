package net.moltaqa.talab_client.data.repository.remote.order

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.order.OrderService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.common.OrderModel
import net.moltaqa.talab_client.data.models.postbody.*
import net.moltaqa.talab_client.data.models.response.order.*
import net.moltaqa.talab_client.data.pagingsource.MyOrdersPagingSource
import net.moltaqa.talab_client.domin.repository.order.OrderRepository
import net.moltaqa.talab_client.util.Constants
import retrofit2.Response
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderService
) : OrderRepository {


    override fun getMyOrders(type: String): Flow<PagingData<OrderModel>> = Pager(
        config = PagingConfig(
            pageSize = Constants.NETWORK_PAGE_SIZE,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { MyOrdersPagingSource(type, api) }
    ).flow

    override suspend fun getOrderDetails(orderId: Int): Response<MainResponseModel<OrderDetailsResponse>> =
        api.getOrderDetails(orderId)


    override suspend fun getOrderDriver(driverId: Int): Response<MainResponseModel<OrderDriverResponse>> =
        api.getOrderDriver(driverId)

    override suspend fun rateOrder(rateOrderBody: RateOrderBody): Response<MainResponseModel<RateOrderResponse>> =
        api.rateOrder(rateOrderBody)

    override suspend fun rateDriver(rateOrderBody: RateOrderBody): Response<MainResponseModel<RateOrderResponse>> =
        api.rateDriver(rateOrderBody)

    override suspend fun getRejectReasons(): Response<MainResponseModel<RejectReasonsResponse>> =
        api.getRejectReasons()


    override suspend fun cancelOrder(
        orderId: Int,
        reasonId: Int
    ): Response<MainResponseModel<MyOrdersResponse>> =
        api.cancelOrder(orderId, reasonId)

    override suspend fun orderFreeOrder(freeOrderBody: FreeOrderBody): Response<MainResponseModel<Any?>> =
        api.orderFreeOrder(freeOrderBody)

    override suspend fun getDriversOffers(orderId: Int): Response<MainResponseModel<DriversOffersResponse>> =
        api.getDriversOffers(orderId)


    override suspend fun responseToOrder(
        responseToOfferBody: ResponseToOfferBody
    ): Response<MainResponseModel<OfferDriverResponse?>> = api.responseToOrder(responseToOfferBody)

    override suspend fun checkOrderPaymentStatus(checkOrderPaymentStatusBody: CheckOrderPaymentStatusBody)
            : Response<MainResponseModel<Any?>> =
        api.checkOrderPaymentStatus(checkOrderPaymentStatusBody)

    override suspend fun refundOrder(
        orderId: Int,
        refundBody: RefundBody
    ): Response<MainResponseModel<Any?>> =
        api.refundOrder(orderId, refundBody)

    override suspend fun getRefunds(type: String): Response<MainResponseModel<RefundsResponse>> =
        api.getRefunds(type)



}