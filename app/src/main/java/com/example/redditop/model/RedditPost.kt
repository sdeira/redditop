package com.example.redditop.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class RedditPost(
    @PrimaryKey
    @SerializedName("name")
    val name: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("score")
    val score: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("subreddit") // technically mutable but fine for a demo
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val subreddit: String,
    @Suppress("ConstructorParameterNaming")
    @SerializedName("num_comments")
    val num_comments: Int,
    @SerializedName("created_utc")
    val created: Long,
    val thumbnail: String?,
    val url: String?,
    val read: Boolean
) {
    var indexInResponse: Int = -1
}
