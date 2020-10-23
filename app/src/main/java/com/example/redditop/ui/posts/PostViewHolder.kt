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
    fun bind(
        postItem: UiModel.PostItem?,
        clearItem: (name: String) -> Unit,
        selectItem: (name: String) -> Unit
    ) {
        postItem?.apply {
            binding.root.setOnClickListener { selectItem(post.name) }
            binding.postItem = this
            binding.dismissPost.setOnClickListener { clearItem(post.name) }
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