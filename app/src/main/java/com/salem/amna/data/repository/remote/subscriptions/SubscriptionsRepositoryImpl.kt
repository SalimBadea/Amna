package net.moltaqa.talab_client.data.repository.remote.subscriptions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.subscriptions.SubscriptionsService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.postbody.CheckSubscribeBody
import net.moltaqa.talab_client.data.models.postbody.SubscribeBody
import net.moltaqa.talab_client.data.models.response.subscriptions.CheckSubscriptionResponse
import net.moltaqa.talab_client.data.models.response.subscriptions.SubscriptionModel
import net.moltaqa.talab_client.data.models.response.subscriptions.SubscriptionResponse
import net.moltaqa.talab_client.data.models.common.payment.PaymentResponse
import net.moltaqa.talab_client.data.pagingsource.SubscriptionsPagingSource
import net.moltaqa.talab_client.domin.repository.subscriptions.SubscriptionRepository
import net.moltaqa.talab_client.util.Constants
import retrofit2.Response
import javax.inject.Inject

class SubscriptionsRepositoryImpl @Inject constructor(
    private val api: SubscriptionsService
) : SubscriptionRepository {


    override fun getSubscriptions(): Flow<PagingData<SubscriptionModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { SubscriptionsPagingSource(api) }
        ).flow
    }

    override suspend fun subscribe(subscribeBody: SubscribeBody): Response<PaymentResponse> =
        api.subscribe(subscribeBody)

    override suspend fun checkSubscribe(checkSubscribeBody: CheckSubscribeBody): Response<MainResponseModel<CheckSubscriptionResponse>> =
        api.checkSubscribe(checkSubscribeBody)




}