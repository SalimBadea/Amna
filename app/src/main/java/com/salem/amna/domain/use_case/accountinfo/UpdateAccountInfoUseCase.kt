package com.salem.amna.domain.use_case.accountinfo

import android.util.Log
import com.salem.amna.data.models.post_body.AccountInfoBody
import com.salem.amna.data.models.response.MainResponseModel
import com.salem.amna.data.models.response.accountinfo.AccountInfoResponse
import com.salem.amna.domain.repository.accountinfo.AccountInfoRepository
import com.salem.amna.util.Resource
import com.salem.amna.util.getErrorResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateAccountInfoUseCase @Inject constructor(
    private val repository: AccountInfoRepository
) {
    private val TAG = "UpdateAccountInfoUse"
    operator fun invoke(accountInfoBody: AccountInfoBody): Flow<Resource<MainResponseModel<AccountInfoResponse>>> = flow {
        try {
            emit(Resource.Loading())
            val myResponse = repository.updateAccountInfo(accountInfoBody)
            Log.d(TAG, "invoke: UpdateAccountInfo use case ${myResponse.isSuccessful}")
            if (myResponse.isSuccessful && myResponse.body() != null) {
                myResponse.body()?.let { response ->
                    emit(Resource.Success(response))
                }
            } else {
                Log.d(TAG, "invoke: UpdateAccountInfo use case errorBody ${myResponse.errorBody()}")
                val errorString = myResponse.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }  // defaults to UTF-8

                val errorMessage =
                    getErrorResponse(errorString!!).errors!![0] ?: ""
                Log.e(TAG, "invoke: Error UpdateAccountInfo use case $errorMessage")
                emit(Resource.Error(errorMessage))
            }

        } catch (e: Exception) {
            Log.e(TAG, "invoke: Exception UpdateAccountInfo use case ${e.localizedMessage}")
            emit(
                Resource.Error(
                    e.localizedMessage?:""
                )
            )
        }
    }
}
