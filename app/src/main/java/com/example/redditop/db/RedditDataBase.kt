package com.example.redditop.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.redditop.model.ReadPost
import com.example.redditop.model.RedditPost
import com.example.redditop.model.RemoteKey

@Database(
    entities = [RedditPost::class, RemoteKey::class, ReadPost::class],
    version = 1,
    exportSchema = false
)
abstract class RedditDataBase : RoomDatabase() {

    abstract fun postDao(): RedditPostDao

    abstract fun remoteKeyDao(): RemoteKeyDao

    abstract fun readPostDao(): ReadPostDao
}
