package com.example.redditop.ui.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.redditop.R
import java.util.concurrent.TimeUnit

/**
 * Load a image view with a url
 */
@BindingAdapter("loadImageFromUrl")
fun loadImageFromUrl(view: ImageView, url: String?) {
    url?.let {
        if (url.startsWith("http")) {
            view.visibility = View.VISIBLE
            Glide.with(view.context).load(url)
                .fitCenter()
                .placeholder(R.drawable.image_placeholder)
                .into(view)
        } else {
            view.visibility = View.GONE
            Glide.with(view.context).clear(view)
        }
    }
}

/**
 * Get the time since the post was created.
 */
@BindingAdapter("createdTime")
fun createdTime(view: TextView, created: Long?) {
    created?.let {
        val currentTime = System.currentTimeMillis()
        val createInMillis = TimeUnit.SECONDS.toMillis(created)
        val diff = currentTime - createInMillis
        val days = TimeUnit.MILLISECONDS.toDays(diff)
        val hours = TimeUnit.MILLISECONDS.toHours(diff)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(diff)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(diff)
        var createdAt = ""
        when {
            days != 0L -> {
                createdAt = view.resources.getString(R.string.days, days.toString())
            }
            hours != 0L -> {
                createdAt = view.resources.getString(R.string.hours, hours.toString())
            }
            minutes != 0L -> {
                createdAt = view.resources.getString(R.string.minutes, minutes.toString())
            }
            seconds != 0L -> {
                createdAt = view.resources.getString(R.string.seconds, seconds.toString())
            }
        }
        view.text = createdAt
    }
}
