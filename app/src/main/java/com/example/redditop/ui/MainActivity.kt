package com.example.redditop.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.redditop.R
import com.example.redditop.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * An activity representing a list of Posts.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<MainActivityBinding>(this, R.layout.main_activity)
    }

}