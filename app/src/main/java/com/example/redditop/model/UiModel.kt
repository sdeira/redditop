package com.example.redditop.model

/**
 * Sealed class to be used only in the ui.
 */
sealed class UiModel {
    data class PostItem(val post: RedditPost) : UiModel()
}
