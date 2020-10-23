package com.example.redditop.di

import android.content.Context
import androidx.room.Room
import com.example.redditop.db.PostsDatabase
import com.example.redditop.db.RedditPostDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): PostsDatabase {
        return Room.databaseBuilder(appContext,
            PostsDatabase::class.java, "Reddit.db")
            .build()
    }

    @Provides
    fun providePostDao(database: PostsDatabase): RedditPostDao {
        return database.postDao()
    }
}