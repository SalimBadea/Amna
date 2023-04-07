package net.moltaqa.talab_client.data.repository.remote.auth

import net.moltaqa.talab_client.data.apiservice.auth.AuthService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.postbody.ChangePasswordBody
import net.moltaqa.talab_client.data.models.postbody.ForgetPasswordBody
import net.moltaqa.talab_client.data.models.postbody.ForgetPasswordCheckCodeBody
import net.moltaqa.talab_client.data.models.postbody.ResetPasswordBody
import net.moltaqa.talab_client.data.models.response.auth.*
import net.moltaqa.talab_client.domin.repository.auth.AuthRepository
import retrofit2.Response
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService
) : AuthRepository {

    override suspend fun getIntroVideo(): Response<MainResponseModel<IntroVideoResponse>> =
        api.getIntroVideo()

    override suspend fun deleteAccount(): Response<MainResponseModel<DeleteAccountResponse>> =
        api.deleteAccount()

    override suspend fun changePassword(changePasswordBody: ChangePasswordBody): Response<MainResponseModel<ChangePasswordResponse>> =
        api.changePassword(changePasswordBody)

    override suspend fun forgetPassword(forgetPasswordBody: ForgetPasswordBody): Response<MainResponseModel<ForgetPasswordResponse>> =
        api.forgetPassword(forgetPasswordBody)

    override suspend fun forgetPasswordCheckCode(forgetPasswordCheckCodeBody: ForgetPasswordCheckCodeBody)
            : Response<MainResponseModel<ForgetPasswordRestResponse>> =
        api.forgetPasswordCheckCode(forgetPasswordCheckCodeBody)

    override suspend fun resetPassword(resetPasswordBody: ResetPasswordBody): Response<MainResponseModel<NewPasswordResponse>> =
        api.resetPassword(resetPasswordBody)


}