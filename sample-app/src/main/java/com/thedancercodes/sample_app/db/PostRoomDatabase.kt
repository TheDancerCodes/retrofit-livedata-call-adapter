package com.thedancercodes.sample_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thedancercodes.sample_app.model.Post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Room is a database layer on top of an SQLite database.
 *
 * Room uses the DAO to issue queries to its database.
 *
 * @Database Annotates class to be a Room Database with a table (entity) of the Word class.
 *
 * getDatabase returns the singleton.
 * It'll create the database the first time it's accessed, using Room's database builder to create
 * a RoomDatabase object in the application context from the WordRoomDatabase class and
 * names it "word_database".
 */
@Database(entities = [Post::class], version = 1, exportSchema = false)
public abstract class PostRoomDatabase : RoomDatabase() {

    // Make database provide its DAOs by creating an abstract "getter" method for each @Dao.
//    abstract fun wordDao(): WordDao

    abstract fun postDao(): PostDao

    /**
     * A custom implementation of the RoomDatabase.Callback(), that also gets a CoroutineScope as
     * constructor parameter.
     *
     * Then, we override the onOpen method to populate the database.
     */
    private class PostDatabaseCallback(private val scope: CoroutineScope)
        : RoomDatabase.Callback() {

        /**
         * Called when the database has been opened.
         *
         * @param db The database.
         */
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.postDao())
                }
            }
        }

        suspend fun populateDatabase(postDao: PostDao) {
            // Delete all content here.
            postDao.deleteAll()

            // Add sample Posts.
            var post = Post(1, 1, "First Post", "First post")
            postDao.insert(post)

            post = Post(1, 2, "Second Post", "Second post")
            postDao.insert(post)

            post = Post(1, 3, "Third Post", "Third post")
            postDao.insert(post)

            post = Post(1, 4, "The Avengers", "Avengers!! Assemble!")
            postDao.insert(post)
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PostRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope): PostRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PostRoomDatabase::class.java,
                    "post_database"
                )
                    .addCallback(PostDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
