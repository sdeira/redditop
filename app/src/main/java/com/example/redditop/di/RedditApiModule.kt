package com.example.redditop.di

import com.example.redditop.api.RedditApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RedditApiModule {

    @Singleton
    @Provides
    fun redditApi(
        okHttpClient: OkHttpClient
    ): RedditApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RedditApi::class.java)
    }

    @Provides
    fun okHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }
}
