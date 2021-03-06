package com.example.redditop.ui.posts

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.redditop.model.UiModel
import javax.inject.Inject

/**
 * Post list paging adapter.
 */
class PostsAdapter @Inject constructor() : PagingDataAdapter<UiModel.PostItem, PostViewHolder>(POST_COMPARATOR) {
    lateinit var clearItem: (name: String) -> Unit
    lateinit var selectItem: (name: String) -> Unit

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val uiModel = getItem(position)
        holder.bind(uiModel, clearItem, selectItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<UiModel.PostItem>() {
            override fun areContentsTheSame(oldItem: UiModel.PostItem, newItem: UiModel.PostItem): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: UiModel.PostItem, newItem: UiModel.PostItem): Boolean =
                oldItem.post.name == newItem.post.name
        }
    }
}
