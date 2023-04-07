package net.moltaqa.talab_client.data.repository.remote.chat

import net.moltaqa.talab_client.data.apiservice.chat.ChatService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.postbody.SendMessageBody
import net.moltaqa.talab_client.data.models.response.chat.GetChatResponse
import net.moltaqa.talab_client.data.models.response.chat.SendMessageResponse
import net.moltaqa.talab_client.domin.repository.chat.ChatRepository
import retrofit2.Response
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val api: ChatService
) : ChatRepository {

    override suspend fun getChat(offerId: Int): Response<MainResponseModel<GetChatResponse>> =
        api.getChat(offerId)


    override suspend fun sendMessage(chatId: Int, sendMessageBody: SendMessageBody): Response<MainResponseModel<SendMessageResponse>> =
        api.sendMessage(chatId,sendMessageBody)


}