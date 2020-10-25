package com.example.redditop.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.redditop.TestCoroutineRule
import com.example.redditop.model.RedditPost
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.com.example.redditop.PostFactory

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RedditDataBaseTest {

    private lateinit var postDao: RedditPostDao
    private lateinit var db: RedditDataBase

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RedditDataBase::class.java).build()
        postDao = db.postDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writePostShouldReturnTheSamePost() {
        testCoroutineRule.runBlockingTest {
            val name = "name_1"
            val posts = ArrayList<RedditPost>()
            posts.add(PostFactory().createRedditPost())

            postDao.insertAll(posts)

            val post = postDao.post(name)
            assertEquals(post?.name, name)
        }
    }

    @Test
    fun clearPostShouldNotReturnThePost() {
        testCoroutineRule.runBlockingTest {
            val name = "name_1"
            val posts = ArrayList<RedditPost>()
            posts.add(PostFactory().createRedditPost())
            posts.add(PostFactory().createRedditPost())
            postDao.insertAll(posts)

            postDao.clearPost(name)

            assertNull(postDao.post(name))
        }
    }

    @Test
    fun markPostAsReadShouldReturnThePostRead() {
        testCoroutineRule.runBlockingTest {
            val name = "name_1"
            val posts = ArrayList<RedditPost>()
            posts.add(PostFactory().createRedditPost())
            postDao.insertAll(posts)

            postDao.markAsRead(true, name)

            postDao.post(name)?.read?.let { assertTrue(it) }
        }
    }
}
