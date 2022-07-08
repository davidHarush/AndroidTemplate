package com.david.haru.myandroidtemplate.di

import com.david.haru.myandroidtemplate.network.IMoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    companion object {
        private const val TIME_OUT = 5L
    }

    @Provides
    @Singleton
    fun provideGlobalOkHttpClient(
    ): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideWebService(
        okHttpClient: OkHttpClient
    ): IMoviesApiService = createWebService(
        okHttpClient,
        IMoviesApiService.BASE_URL
    )


    private inline fun <reified T> createWebService(
        okHttpClient: OkHttpClient,
        url: String,
        converterFactory: Converter.Factory = GsonConverterFactory.create()
    ): T {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(T::class.java)
    }

}