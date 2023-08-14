package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.NotificationsService
import com.salem.amna.data.apiservice.addresses.AddressesServices
import com.salem.amna.data.repository.remote.addresses.AddressesRepositoryImpl
import com.salem.amna.data.repository.remote.notifications.NotificationsRepositoryImpl
import com.salem.amna.domain.repository.addresses.AddressesRepository
import com.salem.amna.domain.repository.notifications.NotificationsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotificationsModule {

    @Provides
    @Singleton
    fun provideNotificationsRepository(api: NotificationsService): NotificationsRepository{
        return NotificationsRepositoryImpl(api)
    }

}