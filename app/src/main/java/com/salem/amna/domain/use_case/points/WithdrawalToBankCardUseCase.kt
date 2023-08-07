package com.salem.amna.domain.use_case.points

import android.util.Log
import com.salem.amna.data.models.post_body.WithdrawalToBankAccountBody
import com.salem.amna.data.models.post_body.WithdrawalToBankCardBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.general.ContactUsResponse
import com.salem.amna.data.models.response.points.WithdrawalsResponse
import com.salem.amna.domain.repository.points.PointsRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WithdrawalToBankCardUseCase @Inject constructor(
    private val repository: PointsRepository
) {

    private val TAG = "WithdrawBankCardUseCase"

    operator fun invoke(body: WithdrawalToBankCardBody): Flow<Resource<MainResponseModel<ContactUsResponse>>> = flow {
        try {
            emit(Resource.Loading())

            val myResponse = repository.withdrawalToBankCard(body)
            Log.d(TAG, "invoke: WithdrawBankCard use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: WithdrawBankCard use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader()
                    .use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).message ?: ""
                Log.e(TAG, "invoke: Error WithdrawBankCard use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        }catch (e: Exception) {
            Log.e(TAG, "invoke: Exception WithdrawBankCard use case ${e.localizedMessage}")
            emit(Resource.Error(e.localizedMessage ?: ""))
        }
    }
}