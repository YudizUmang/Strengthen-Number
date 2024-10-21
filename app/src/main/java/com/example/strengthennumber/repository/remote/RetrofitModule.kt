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
    @Singleton
    fun providesRetrofit() : UserApi{
       val retrofit by lazy{ Retrofit.Builder().baseUrl("https://strengthen-numbers.dev-imaginovation.net/api/v2")
            .addConverterFactory(GsonConverterFactory.create()).build()}

        return retrofit.create(UserApi::class.java)

    }
}