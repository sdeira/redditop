package com.example.redditop.api

import com.example.redditop.model.RedditPost
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {
    @GET("/top.json")
    suspend fun getTop(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): ListingResponse

    class ListingResponse(val data: ListingData)

    class ListingData(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?
    )
    data class RedditChildrenResponse(val data: RedditPost)
}
