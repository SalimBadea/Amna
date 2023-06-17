package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.cart.CartServices
import com.salem.amna.data.repository.remote.cart.CartRepositoryImpl
import com.salem.amna.domain.repository.cart.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {

    @Provides
    @Singleton
    fun provideCartRepository(api: CartServices): CartRepository{
        return CartRepositoryImpl(api)
    }
}