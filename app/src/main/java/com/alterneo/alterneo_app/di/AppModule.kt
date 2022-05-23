package com.alterneo.alterneo_app.di

import com.alterneo.alterneo_app.core.data.remote.AlterneoAPI
import com.alterneo.alterneo_app.utils.Constants
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    fun provideClient(): AlterneoAPI = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AlterneoAPI::class.java)
}