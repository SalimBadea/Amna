package net.moltaqa.talab_client.data.repository.remote.notification

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.blog.BlogService
import net.moltaqa.talab_client.data.apiservice.notification.NotificationService
import net.moltaqa.talab_client.data.models.response.blog.BlogModel
import net.moltaqa.talab_client.data.models.response.notification.NotificationModel
import net.moltaqa.talab_client.data.pagingsource.BlogsPagingSource
import net.moltaqa.talab_client.data.pagingsource.NotificationsPagingSource
import net.moltaqa.talab_client.domin.repository.notification.NotificationRepository
import net.moltaqa.talab_client.util.Constants.NETWORK_PAGE_SIZE
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val api: NotificationService
) : NotificationRepository {


    override fun getAllNotifications(): Flow<PagingData<NotificationModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { NotificationsPagingSource(api) }
        ).flow
    }


}