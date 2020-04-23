package com.thedancercodes.sample_app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thedancercodes.sample_app.db.PostRoomDatabase
import com.thedancercodes.sample_app.model.Post
import com.thedancercodes.sample_app.repo.PostRepository
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class PostViewModel(application: Application) : AndroidViewModel(application) {

    // private member variable to hold a reference to the repository.
    // The ViewModel maintains a reference to the repository to get data.
    private val repository: PostRepository

    // public LiveData member variable to cache the list of posts.
    // LiveData gives us updated posts when they change.
    val allPosts: LiveData<List<Post>>

    init {

        // Gets reference to PostDao from PostRoomDatabase to construct
        // the correct PostRepository.
        val wordsDao = PostRoomDatabase.getDatabase(application, viewModelScope).postDao()
        repository = PostRepository(wordsDao)

        // Initialized the allPosts LiveData using the repository.
        allPosts = repository.allPosts
    }

    /**
     * A wrapper insert() method that calls the Repository's insert() method.
     *
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(post: Post) = viewModelScope.launch {
        repository.insert(post)
    }
}