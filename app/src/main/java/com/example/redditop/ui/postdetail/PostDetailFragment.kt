package com.example.redditop.ui.postdetail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.example.redditop.R
import com.example.redditop.databinding.PostDetailFragmentBinding
import com.example.redditop.model.UiModel
import com.example.redditop.saveImage
import com.example.redditop.viewmodel.PostsViewModel
import kotlinx.coroutines.launch

/**
 * A fragment representing a single Post detail screen.
 */
@ExperimentalPagingApi
class PostDetailFragment : Fragment() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 22222
    }
    private lateinit var binding: PostDetailFragmentBinding
    private val viewModel: PostsViewModel by activityViewModels()
    private val args: PostDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PostDetailFragmentBinding.inflate(inflater, container, false)

        initPostItem()
        initShowBack()
        initDownloadImage()
        return binding.root
    }

    /**
     * Get the post item from the data base by the name get by params.
     */
    private fun initPostItem() {
        lifecycleScope.launch {
            val post = viewModel.getPost(args.postName)
            post?.let {
                binding.postItem = UiModel.PostItem(post)
            }
        }
    }

    /**
     * Check if we have to show the back icon in the toolbar,
     * if we are in landscape we shouldn't show it.
     */
    private fun initShowBack() {
        if (args.showBack) {
            binding.detailToolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }
        } else {
            binding.detailToolbar.navigationIcon = null
        }
    }

    /**
     * Init the click listener to download the image.
     */
    private fun initDownloadImage() {
        binding.download.setOnClickListener {
            if (hasPermission()) {
                binding.postItem?.let { postItem ->
                    context?.apply {
                        postItem.post.url?.let { url ->
                            if (url.endsWith(".jpg")) {
                                url.saveImage(this, postItem.post.name)
                            } else {
                                postItem.post.thumbnail?.saveImage(this, postItem.post.name)
                            }
                        }
                    }
                }
            } else {
                requestPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    PERMISSION_REQUEST_CODE)
            }
        }
    }

    /**
     * Check if the user has the read external permission
     * @return true if yes, false if not.
     */
    private fun hasPermission(): Boolean {
        context?.let { context ->
            val permission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)
            return permission == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                context?.apply {
                    if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, resources.getString(R.string.download_image_without_permissions),
                            Toast.LENGTH_LONG).show()
                    } else {
                        binding.postItem?.let {
                            it.post.thumbnail?.saveImage(this, it.post.name)
                        }
                    }
                }
            }
        }
    }
}
