package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.points.PointsServices
import com.salem.amna.data.repository.remote.points.PointsRepositoryImpl
import com.salem.amna.domain.repository.points.PointsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PointsModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(api: PointsServices): PointsRepository {
        return PointsRepositoryImpl(api)
    }
}