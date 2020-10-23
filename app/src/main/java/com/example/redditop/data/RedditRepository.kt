package com.example.redditop.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.redditop.api.RedditApi
import com.example.redditop.db.RedditDataBase
import com.example.redditop.model.RedditPost
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class RedditRepository @Inject constructor(
    private val redditApi: RedditApi,
    private val dataBase: RedditDataBase
) {
    companion object {
        private const val NETWORK_PAGE_SIZE = 25
    }

    fun getSearchResultStream(): Flow<PagingData<RedditPost>> {
        val pagingSourceFactory =  { dataBase.postDao().posts() }

        return Pager(config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            remoteMediator = RedditRemoteMediator(
                redditApi,
                dataBase
            ),
            pagingSourceFactory = pagingSourceFactory).flow
    }
}