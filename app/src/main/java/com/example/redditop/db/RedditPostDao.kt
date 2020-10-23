package com.example.redditop.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditop.model.RedditPost

@Dao
interface RedditPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<RedditPost>)

    @Query("DELETE FROM posts")
    suspend fun clearPosts()

    @Query("SELECT * FROM posts ORDER BY indexInResponse ASC")
    fun posts(): PagingSource<Int, RedditPost>
}
