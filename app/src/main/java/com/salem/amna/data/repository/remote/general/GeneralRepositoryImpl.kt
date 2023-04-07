package net.moltaqa.talab_client.data.repository.remote.general

import com.google.android.gms.maps.model.LatLng
import net.moltaqa.talab_client.data.apiservice.general.GeneralService
import net.moltaqa.talab_client.data.models.MainResponseModel
import net.moltaqa.talab_client.data.models.common.GoogleDirectionModel
import net.moltaqa.talab_client.data.models.postbody.ContactUsBody
import net.moltaqa.talab_client.data.models.postbody.GiftCardBody
import net.moltaqa.talab_client.data.models.response.general.ContactUsResponse
import net.moltaqa.talab_client.data.models.response.general.GeneralResponse
import net.moltaqa.talab_client.data.models.response.general.PurchaseCouponResponse
import net.moltaqa.talab_client.data.models.response.region.AreaResponse
import net.moltaqa.talab_client.data.models.response.walt.WaltPointsResponse
import net.moltaqa.talab_client.domin.repository.general.GeneralRepository
import net.moltaqa.talab_driver.data.models.response.region.RegionResponse
import retrofit2.Response
import javax.inject.Inject

class GeneralRepositoryImpl @Inject constructor(
    private val api: GeneralService
) : GeneralRepository{
    override suspend fun getGeneralData(page: Int): Response<MainResponseModel<GeneralResponse>>

    = api.getGeneral(page)

    override suspend fun contactUs(contactUsBody: ContactUsBody): Response<MainResponseModel<ContactUsResponse>>
        =  api.contactUs(contactUsBody)

    override suspend fun getAreas(): Response<MainResponseModel<AreaResponse>> = api.getAreas()

    override suspend fun getCities(areaId: Int): Response<MainResponseModel<RegionResponse>>

            = api.getCities(areaId)

    override suspend fun getWaltPoints(): Response<MainResponseModel<WaltPointsResponse>> =
        api.getWaltPoints()

    override suspend fun purchaseCoupon(giftCardBody: GiftCardBody): Response<MainResponseModel<PurchaseCouponResponse>> =
        api.purchaseCoupon(giftCardBody)

    override suspend fun checkGiftCardStatus(giftCardBody: GiftCardBody): Response<MainResponseModel<Any?>> =
        api.checkGiftCardStatus(giftCardBody)

    override suspend fun getDistanceAndTime(
        startLatLng: LatLng,
        endLatLng: LatLng
    ): Response<GoogleDirectionModel> =
        api.getDistanceAndTime(
            startLatlng = "${startLatLng.latitude},${startLatLng.longitude}",
            endLatlng = "${endLatLng.latitude},${endLatLng.longitude}",
        )


}