package com.example.redditop.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts_read")
class ReadPost(
    @PrimaryKey
    val name: String
)
