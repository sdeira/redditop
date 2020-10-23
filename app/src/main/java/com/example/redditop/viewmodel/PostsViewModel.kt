package com.example.redditop.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.redditop.data.RedditRepository
import com.example.redditop.model.UiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalPagingApi
class PostsViewModel @ViewModelInject constructor(
    private val repository: RedditRepository
) : ViewModel() {
    private var currentSearchResult: Flow<PagingData<UiModel.PostItem>>? = null

    /**
     * Get Posts from the repository if we don't get it already
     */
    fun getPosts(): Flow<PagingData<UiModel.PostItem>> {
        val lastResult = currentSearchResult
        if (lastResult != null) {
            return lastResult
        }
        val newResult = repository.getSearchResultStream().cachedIn(viewModelScope)
            .map { pagingData -> pagingData.map { UiModel.PostItem(it) } }
        currentSearchResult = newResult
        return newResult
    }
    suspend fun clearPostByName(name: String) {
        repository.clearPostByName(name)
    }
}