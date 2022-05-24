package com.alterneo.alterneo_app.di

import com.alterneo.alterneo_app.BuildConfig
import com.alterneo.alterneo_app.core.data.remote.AlterneoAPI
import com.alterneo.alterneo_app.feature_map.data.remote.repository.RepositoryImpl
import com.alterneo.alterneo_app.feature_map.domain.repository.Repository
import com.alterneo.alterneo_app.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideClient(): AlterneoAPI {
        val okClient = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            okClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return Retrofit.Builder()
            .client(okClient.build())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlterneoAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideMapRepository(api: AlterneoAPI): Repository {
        return RepositoryImpl(api)
    }
}