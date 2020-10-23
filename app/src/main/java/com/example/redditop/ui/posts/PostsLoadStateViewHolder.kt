package com.example.redditop.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.redditop.R
import com.example.redditop.databinding.LoadStateFooterPostViewItemBinding

class PostsLoadStateViewHolder(
    private val binding: LoadStateFooterPostViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    fun bind(loadState: LoadState) {
        binding.loadState = loadState
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): PostsLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.load_state_footer_post_view_item, parent, false)
            val binding = LoadStateFooterPostViewItemBinding.bind(view)
            return PostsLoadStateViewHolder(binding, retry)
        }
    }
}