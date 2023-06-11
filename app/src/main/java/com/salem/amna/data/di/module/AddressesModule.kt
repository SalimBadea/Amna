package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.addresses.AddressesServices
import com.salem.amna.data.repository.remote.addresses.AddressesRepositoryImpl
import com.salem.amna.domain.repository.addresses.AddressesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddressesModule {

    @Provides
    @Singleton
    fun provideAddressesRepository(api: AddressesServices): AddressesRepository{
        return AddressesRepositoryImpl(api)
    }

}