package com.example.redditop.viewmodel

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.ExperimentalPagingApi
import com.example.redditop.TestCoroutineRule
import com.example.redditop.data.RedditRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class, sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class PostsViewModelTest {
    lateinit var postsViewModel: PostsViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var repository: RedditRepository

    lateinit var spyRepository: RedditRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
        spyRepository = Mockito.spy(repository)
        postsViewModel = PostsViewModel(spyRepository)
    }

    @Test
    fun `test getting post by name should call repository to get post`() =
        testCoroutineRule.runBlockingTest {
            val name = "name"
            Mockito.doReturn(null).`when`(spyRepository).getPost(name)

            postsViewModel.getPost(name)

            Mockito.verify(spyRepository).getPost(name)
        }

    @Test
    fun `test getting posts should call repository to get posts`() =
        testCoroutineRule.runBlockingTest {

            postsViewModel.getPosts()

            Mockito.verify(spyRepository).getSearchResultStream()
        }

    @Test
    fun `test clear all posts should call repository to clear posts`() =
        testCoroutineRule.runBlockingTest {
            Mockito.doReturn(null).`when`(spyRepository).clearAllPosts()

            postsViewModel.clearAllPosts()

            Mockito.verify(spyRepository).clearAllPosts()
        }

    @Test
    fun `test clear posts by name should call repository to clear post`() =
        testCoroutineRule.runBlockingTest {
            val name = "name"
            Mockito.doReturn(null).`when`(spyRepository).clearPostByName(name)

            postsViewModel.clearPostByName(name)

            Mockito.verify(spyRepository).clearPostByName(name)
        }

    @Test
    fun `test mark post as read should call repository to mark post as read`() =
        testCoroutineRule.runBlockingTest {
            val name = "name"
            Mockito.doReturn(null).`when`(spyRepository).markPostAsRead(name)

            postsViewModel.markPostAsRead(name)

            Mockito.verify(spyRepository).markPostAsRead(name)
        }
}
