package com.karegraf.androidarchitecturestarterkit.data.di

import android.content.SharedPreferences
import com.karegraf.androidarchitecturestarterkit.data.local.TokenManager
import com.karegraf.androidarchitecturestarterkit.data.remote.API
import com.karegraf.androidarchitecturestarterkit.data.remote.RepositoryImp
import com.karegraf.androidarchitecturestarterkit.data.remote.TokenManagerImp
import com.karegraf.androidarchitecturestarterkit.data.remote.Interceptor.AuthInterceptor
import com.karegraf.androidarchitecturestarterkit.domain.repository.MyRepository
import com.karegraf.androidarchitecturestarterkit.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)

object AppModule {
    @Provides
    @Singleton
    fun provideMyRepository(api: API): MyRepository {
        return RepositoryImp(api = api)
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun provideAPI(okHttpClient: OkHttpClient): API {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(API::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManagerImp(sharedPreferences)
    }

}