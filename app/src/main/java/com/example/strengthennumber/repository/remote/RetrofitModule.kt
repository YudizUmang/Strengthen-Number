package com.example.strengthennumber.repository.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

        @Provides
        fun provideBaseUrl(): String = "https://strengthen-numbers.dev-imaginovation.net/api/v2/"

        @Provides
        @Singleton
        fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): UserApi= retrofit.create(UserApi::class.java)

}