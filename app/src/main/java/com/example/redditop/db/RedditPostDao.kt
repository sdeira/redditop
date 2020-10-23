package com.example.redditop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.redditop.model.RedditPost

@Dao
interface RedditPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<RedditPost>)
}
