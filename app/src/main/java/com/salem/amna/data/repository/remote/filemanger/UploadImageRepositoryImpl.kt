package net.moltaqa.talab_client.data.repository.remote.filemanger

import net.moltaqa.talab_client.data.apiservice.filemanger.FileMangerService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.UploadFileResponse
import net.moltaqa.talab_client.domin.repository.filemanger.UploadImageRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Response
import java.io.File

class UploadImageRepositoryImpl(
    private val api: FileMangerService
) : UploadImageRepository {
    override suspend fun uploadImage(file: File): Response<MainResponseModel<UploadFileResponse?>> {
        val requestBody = file.asRequestBody("multipart/from-data".toMediaTypeOrNull())
        val imageBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
        return api.uploadPhoto(imageBody)
    }


}