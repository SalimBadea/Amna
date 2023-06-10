package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.general.GeneralService
import com.salem.amna.data.repository.remote.general.GeneralRepositoryImpl
import com.salem.amna.domain.repository.general.GeneralRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeneralModule {

    @Provides
    @Singleton
    fun provideGeneralRepository(api: GeneralService): GeneralRepository {
        return GeneralRepositoryImpl(api)
    }
}