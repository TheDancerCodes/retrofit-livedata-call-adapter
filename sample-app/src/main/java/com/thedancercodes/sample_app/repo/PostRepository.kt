package com.thedancercodes.sample_app.repo

import androidx.lifecycle.LiveData
import com.thedancercodes.sample_app.db.PostDao
import com.thedancercodes.sample_app.model.Post

/**
 * Declares the DAO as a private property in the constructor. Pass in the DAO instead of the
 * whole database, because you only need access to the DAO.
 *
 * The DAO contains all the read/write methods for the database.
 */
class PostRepository(private val postDao: PostDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allPosts: LiveData<List<Post>> = postDao.getAlphabetizedPosts()

    // The suspend modifier tells the compiler that this needs to be called from a coroutine or
    // another suspending function.
    suspend fun insert(post: Post) {
        postDao.insert(post)
    }
}