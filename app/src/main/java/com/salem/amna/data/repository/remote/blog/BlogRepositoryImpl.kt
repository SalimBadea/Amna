package net.moltaqa.talab_client.data.repository.remote.blog

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.moltaqa.talab_client.data.apiservice.blog.BlogService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.response.blog.BlogModel
import net.moltaqa.talab_client.data.models.response.blog.BlogResponse
import net.moltaqa.talab_client.data.pagingsource.BlogsPagingSource
import net.moltaqa.talab_client.domin.repository.blog.BlogRepository
import net.moltaqa.talab_client.util.Constants.NETWORK_PAGE_SIZE
import retrofit2.Response
import javax.inject.Inject

class BlogRepositoryImpl @Inject constructor(
    private val api: BlogService
) : BlogRepository {
    override fun getBlogs(): Flow<PagingData<BlogModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { BlogsPagingSource(api) }
        ).flow
    }

    override suspend fun getBlog(blogId: Int): Response<MainResponseModel<BlogResponse>>
      = api.getBlog(blogId)



}