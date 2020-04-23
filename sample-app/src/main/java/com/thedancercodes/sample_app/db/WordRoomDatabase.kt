package com.thedancercodes.sample_app.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.thedancercodes.sample_app.model.Word
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
@Database(entities = [Word::class], version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {

    // Make database provide its DAOs by creating an abstract "getter" method for each @Dao.
    abstract fun wordDao(): WordDao

    /**
     * A custom implementation of the RoomDatabase.Callback(), that also gets a CoroutineScope as
     * constructor parameter.
     *
     * Then, we override the onOpen method to populate the database.
     */
    private class WordDatabaseCallback(private val scope: CoroutineScope)
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
                    populateDatabase(database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            // Delete all content here.
            wordDao.deleteAll()

            // Add sample words.
            var word = Word("Niaje")
            wordDao.insert(word)

            word = Word("Vipi?")
            wordDao.insert(word)

            word = Word("Cheki!")
            wordDao.insert(word)
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope): WordRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
