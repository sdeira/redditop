package com.example.redditop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.redditop.model.RedditPost

@Database(
    entities = [RedditPost::class],
    version = 1,
    exportSchema = false
)
abstract class PostsDatabase : RoomDatabase() {

    abstract fun postDao(): RedditPostDao

}