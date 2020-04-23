package com.thedancercodes.sample_app.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.thedancercodes.sample_app.model.Post

/**
 * In the DAO (data access object), you specify SQL queries and associate them with method calls.
 * The compiler checks the SQL and generates queries from convenience annotations for common queries,
 * such as @Insert.
 *
 * Allowing your queries to be annotated with the suspend modifier and then called from a coroutine
 * or from another suspension function.
 */
@Dao
interface PostDao {

    /* Getting all words ordered alphabetically */
    @Query("SELECT * from post_table ORDER BY post ASC")
    fun getAlphabetizedPosts(): LiveData<List<Post>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(post: Post)

    /* Deleting all words */
    @Query("DELETE FROM post_table")
    suspend fun deleteAll()

}