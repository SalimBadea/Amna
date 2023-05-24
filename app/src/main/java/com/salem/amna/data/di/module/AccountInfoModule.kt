package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.accountinfo.AccountInfoService
import com.salem.amna.data.repository.remote.accountinfo.AccountInfoRepositoryImpl
import com.salem.amna.domain.repository.accountinfo.AccountInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AccountInfoModule {

    @Provides
    @Singleton
    fun provideAccountInfoRepository(api: AccountInfoService): AccountInfoRepository {
        return AccountInfoRepositoryImpl(api)
    }





}