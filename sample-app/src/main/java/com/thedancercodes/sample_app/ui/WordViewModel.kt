package com.thedancercodes.sample_app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.thedancercodes.sample_app.db.WordRoomDatabase
import com.thedancercodes.sample_app.model.Word
import com.thedancercodes.sample_app.repo.WordRepository
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class WordViewModel(application: Application) : AndroidViewModel(application) {

    // private member variable to hold a reference to the repository.
    // The ViewModel maintains a reference to the repository to get data.
    private val repository: WordRepository

    // public LiveData member variable to cache the list of words.
    // LiveData gives us updated words when they change.
    val allWords: LiveData<List<Word>>

    init {

        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)

        // Initialized the allWords LiveData using the repository.
        allWords = repository.allWords
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
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}