package com.thedancercodes.sample_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thedancercodes.sample_app.model.Word

/**
 * In the DAO (data access object), you specify SQL queries and associate them with method calls.
 * The compiler checks the SQL and generates queries from convenience annotations for common queries,
 * such as @Insert.
 *
 * Allowing your queries to be annotated with the suspend modifier and then called from a coroutine
 * or from another suspension function.
 */
@Dao
interface WordDao {

    /* Getting all words ordered alphabetically */
    @Query("SELECT * from word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): LiveData<List<Word>>

    /* Inserting a word */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    /* Deleting all words */
    @Query("DELETE FROM word_table")
    suspend fun deleteAll()
}