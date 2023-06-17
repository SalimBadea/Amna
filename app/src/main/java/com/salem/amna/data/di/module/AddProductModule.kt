package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.add_product.AddProductServices
import com.salem.amna.data.repository.remote.add_product.AddProductRepositoryImpl
import com.salem.amna.domain.repository.add_product.AddProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AddProductModule {

    @Provides
    @Singleton
    fun provideAddProductRepository(api:AddProductServices): AddProductRepository{
        return AddProductRepositoryImpl(api)
    }
}