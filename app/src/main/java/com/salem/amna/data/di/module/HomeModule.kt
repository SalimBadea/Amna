package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.home.HomeServices
import com.salem.amna.data.repository.remote.HomeRepositoryImpl
import com.salem.amna.domain.repository.home.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeModule {

    @Provides
    @Singleton
    fun provideHomeRepository(api: HomeServices):HomeRepository{
        return HomeRepositoryImpl(api)
    }
}