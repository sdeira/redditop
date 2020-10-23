package com.example.redditop.ui.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.redditop.R
import java.util.concurrent.TimeUnit

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