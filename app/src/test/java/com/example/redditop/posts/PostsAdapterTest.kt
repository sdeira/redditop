package com.example.redditop.posts

import android.os.Build
import android.widget.LinearLayout
import androidx.paging.PagingData
import androidx.test.core.app.ApplicationProvider
import com.example.redditop.TestCoroutineRule
import com.example.redditop.model.RedditPost
import com.example.redditop.model.UiModel
import com.example.redditop.ui.posts.PostViewHolder
import com.example.redditop.ui.posts.PostsAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@Config(sdk = [Build.VERSION_CODES.P])
@RunWith(RobolectricTestRunner::class)
class PostsAdapterTest {
    lateinit var adapter: PostsAdapter

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        adapter = spy(PostsAdapter::class.java)
    }

    @Test
    fun testCreateViewHolder() {
        val viewHolder = adapter.onCreateViewHolder(LinearLayout(ApplicationProvider.getApplicationContext()), 0)
        Assert.assertNotNull(viewHolder)
    }

    @Test
    fun testBindViewHolder() {
        testCoroutineRule.runBlockingTest {
            val viewHolder: PostViewHolder =
                spy(adapter.onCreateViewHolder(LinearLayout(ApplicationProvider.getApplicationContext()), 0))
            val list = ArrayList<UiModel.PostItem>()
            val post = UiModel.PostItem(RedditPost("name", "title", 0, "author",
                "subreddit", 0, 2323, "o", "d", false))
            list.add(post)
            val pagingData: PagingData<UiModel.PostItem> = PagingData.from(list)
            adapter.submitData(pagingData)
            val clearItemBlock: (name: String) -> Unit = {}
            val selectItemBlock: (name: String) -> Unit = {}
            adapter.clearItem = clearItemBlock
            adapter.selectItem = selectItemBlock

            adapter.bindViewHolder(viewHolder, 0)

            verify(viewHolder).bind(post, clearItemBlock, selectItemBlock)
        }
    }
}
