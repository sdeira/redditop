package com.example.redditop.ui.bindingadapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.redditop.R

@BindingAdapter("loadImageFromUrl")
fun loadImageFromUrl(view: ImageView, thumbnail: String?) {
    thumbnail?.let {
        if (thumbnail.startsWith("http")) {
            view.visibility = View.VISIBLE
            Glide.with(view.context).load(thumbnail)
                .centerInside()
                .placeholder(R.drawable.image_placeholder)
                .into(view)
        } else {
            view.visibility = View.GONE
            Glide.with(view.context).clear(view)
        }
    }
}