package com.example.redditop.model

sealed class UiModel {
    data class PostItem(val post: RedditPost) : UiModel()
}
