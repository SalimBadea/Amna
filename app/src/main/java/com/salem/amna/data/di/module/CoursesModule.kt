package com.salem.amna.data.di.module

import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.apiservice.categories.CategoriesService
import com.salem.amna.data.apiservice.courses.CoursesService
import com.salem.amna.data.repository.remote.auth.LoginRepositoryImpl
import com.salem.amna.data.repository.remote.categories.CategoriesRepositoryImpl
import com.salem.amna.data.repository.remote.courses.CoursesRepositoryImpl
import com.salem.amna.domain.repository.auth.LoginRepository
import com.salem.amna.domain.repository.categories.CategoriesRepository
import com.salem.amna.domain.repository.courses.CoursesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoursesModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(api: CoursesService): CoursesRepository {
        return CoursesRepositoryImpl(api)
    }
}