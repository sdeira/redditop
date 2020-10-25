package java.com.example.redditop

import com.example.redditop.model.RedditPost
import java.util.concurrent.atomic.AtomicInteger

class PostFactory {
    private val counter = AtomicInteger(0)
    fun createRedditPost(): RedditPost {
        val id = counter.incrementAndGet()
        val post = RedditPost(
            name = "name_$id",
            title = "title $id",
            score = 10,
            author = "author $id",
            num_comments = 0,
            created = System.currentTimeMillis(),
            thumbnail = null,
            subreddit = "subreddit_$id",
            url = null,
            read = false
        )
        post.indexInResponse = -1
        return post
    }
}
