package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.apiservice.categories.CategoriesService
import com.salem.amna.data.repository.remote.auth.LoginRepositoryImpl
import com.salem.amna.data.repository.remote.categories.CategoriesRepositoryImpl
import com.salem.amna.domain.repository.auth.LoginRepository
import com.salem.amna.domain.repository.categories.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriesModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(api: CategoriesService): CategoriesRepository {
        return CategoriesRepositoryImpl(api)
    }
}