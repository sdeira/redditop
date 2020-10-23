package com.example.redditop.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.redditop.databinding.PostViewItemBinding
import com.example.redditop.model.UiModel

/**
 * View Holder for a [UiModel.PostItem] recycler view item.
 */
class PostViewHolder(
    private val binding: PostViewItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(postItem: UiModel.PostItem?) {
        postItem?.let {
            binding.postItem = postItem
        }
    }

    companion object {
        fun create(parent: ViewGroup): PostViewHolder {
            return PostViewHolder(
                PostViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}