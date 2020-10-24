package com.example.redditop.ui.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.redditop.databinding.PostDetailFragmentBinding
import com.example.redditop.model.UiModel
import com.example.redditop.viewmodel.PostsViewModel
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Post detail screen.
 */
@ExperimentalPagingApi
class PostDetailFragment : Fragment() {
    private lateinit var binding: PostDetailFragmentBinding
    private val viewModel: PostsViewModel by activityViewModels()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostDetailFragmentBinding.inflate(inflater, container, false)
        lifecycleScope.launch {
            val post = viewModel.getPost(args.postName)
            post?.let {
                binding.postItem = UiModel.PostItem(post)
            }
        }
        if (args.showBack) {
            binding.detailToolbar.setNavigationOnClickListener {view ->
                view.findNavController().navigateUp()
            }
        } else {
            binding.detailToolbar.navigationIcon = null
        }
        return binding.root
    }
}