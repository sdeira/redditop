package com.example.redditop.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.redditop.api.RedditApi
import com.example.redditop.db.RedditDataBase
import com.example.redditop.model.RedditPost
import com.example.redditop.model.RemoteKey
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class RedditRemoteMediator(
    private val service: RedditApi,
    private val redditDataBase: RedditDataBase
) : RemoteMediator<Int, RedditPost>() {

    companion object {
        private const val REMOTE_KEY_ID = "remoteKey"
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RedditPost>
    ): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = redditDataBase.withTransaction { redditDataBase.remoteKeyDao().remoteKey() }
                    remoteKey?.let {
                        if (remoteKey.nextPageKey == null) {
                            return MediatorResult.Success(endOfPaginationReached = true)
                        }
                        remoteKey.nextPageKey
                    }
                }
            }

            val data = service.getTop(after = loadKey, before = null, limit = when (loadType) {
                LoadType.REFRESH -> state.config.initialLoadSize
                else -> state.config.pageSize
            }).data

            val items = data.children.map { it.data }
            redditDataBase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    redditDataBase.postDao().clearPosts()
                    redditDataBase.remoteKeyDao().delete()
                }

                redditDataBase.remoteKeyDao().insert(RemoteKey(REMOTE_KEY_ID, data.after))
                redditDataBase.postDao().insertAll(items)
            }
            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

}