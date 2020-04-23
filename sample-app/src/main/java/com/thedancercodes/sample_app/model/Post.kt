package com.thedancercodes.sample_app.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This class will describe the Entity (which represents the SQLite table) for your words.
 * Each public property in the class represents a column in the table.
 *
 * Room will ultimately use these properties to both create the table and instantiate objects
 * from rows in the database.
 *
 * @Entity class represents a SQLite table.
 *
 * @PrimaryKey - Every entity needs a primary key. To keep things simple, each word acts as its
 * own primary key.
 *
 * @ColumnInfo(name = "post") Specifies the name of the column in the table if you want it to be
 * different from the name of the member variable. This names the column "post".
 */
@Entity(tableName = "post_table")
data class Post(
    @SerializedName("userId")
    @Expose
    val userId: Int,

    @SerializedName("id")
    @Expose
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("body")
    @Expose
    @ColumnInfo(name = "post")
    val body: String
)