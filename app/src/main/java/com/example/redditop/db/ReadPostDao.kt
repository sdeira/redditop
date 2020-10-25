package com.example.redditop.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.redditop.model.ReadPost

@Dao
interface ReadPostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(readPost: ReadPost)

    @Query("SELECT * FROM posts_read")
    suspend fun postsRead(): List<ReadPost>

    @Query("DELETE FROM posts_read")
    suspend fun delete()
}
