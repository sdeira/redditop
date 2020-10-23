package com.example.redditop.ui.postdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.redditop.databinding.PostDetailFragmentBinding

/**
 * A fragment representing a single Post detail screen.
 */
class PostDetailFragment : Fragment() {
    private lateinit var binding: PostDetailFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
}