package com.example.redditop.api

import com.example.redditop.model.RedditPost
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The reddit api to get the information.
 */
interface RedditApi {
    @GET("/top.json")
    suspend fun getTop(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): ListingResponse

    /**
     * The listing response of reddit.
     */
    class ListingResponse(val data: ListingData)

    /**
     * The listing data of reddit.
     */
    class ListingData(
        val children: List<RedditChildrenResponse>,
        val after: String?,
        val before: String?
    )

    /**
     * The children response of reddit.
     */
    data class RedditChildrenResponse(val data: RedditPost)
}
