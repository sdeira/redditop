package com.example.redditop.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.redditop.databinding.PostsFragmentBinding
import com.example.redditop.viewmodel.PostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.content_layout.view.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@AndroidEntryPoint
class PostsFragment : Fragment() {
    private var postsJob: Job? = null
    private lateinit var binding: PostsFragmentBinding
    private val viewModel: PostsViewModel by activityViewModels()
    @Inject lateinit var adapter: PostsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostsFragmentBinding.inflate(inflater, container, false)
        initList()
        initAdapter()
        initClearAll()
        getPosts()
        return binding.root
    }

    private fun initList() {
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.content.list.addItemDecoration(decoration)
        binding.content.swipe_to_refresh.setOnRefreshListener { adapter.refresh() }
    }

    private fun initClearAll() {
        binding.dismissAll.setOnClickListener { viewLifecycleOwner.lifecycleScope.launch {
            viewModel.clearAllPosts()
        } }
    }

    private fun getPosts() {
        // Make sure we cancel the previous job before creating a new one
        postsJob?.cancel()
        postsJob = viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPosts().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initAdapter() {
        binding.content.list.adapter = adapter.withLoadStateFooter(footer = PostsLoadStateAdapter { adapter.retry() })
        adapter.clearItem = { name -> viewLifecycleOwner.lifecycleScope.launch { viewModel.clearPostByName(name) } }
        adapter.selectItem = {
                name -> viewLifecycleOwner.lifecycleScope.launch {
            viewModel.markPostAsRead(name)
        }
        }

        adapter.addLoadStateListener { loadState ->
            binding.content.swipe_to_refresh.isRefreshing = loadState.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadState.refresh is LoadState.Error && adapter.itemCount == 0
        }
    }
}