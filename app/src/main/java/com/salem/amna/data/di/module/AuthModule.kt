package com.salem.amna.data.di.module


import com.salem.amna.data.apiservice.auth.AuthService
import com.salem.amna.data.repository.remote.auth.LoginRepositoryImpl
import com.salem.amna.data.repository.remote.auth.RegisterRepositoryImpl
import com.salem.amna.data.repository.remote.auth.VerifyRepositoryImpl
import com.salem.amna.domain.repository.auth.LoginRepository
import com.salem.amna.domain.repository.auth.RegisterRepository
import com.salem.amna.domain.repository.auth.VerifyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideLoginRepository(api: AuthService): LoginRepository {
        return LoginRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRegisterRepository(api: AuthService): RegisterRepository {
        return RegisterRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideVerifyRepository(api: AuthService): VerifyRepository {
        return VerifyRepositoryImpl(api)
    }

}