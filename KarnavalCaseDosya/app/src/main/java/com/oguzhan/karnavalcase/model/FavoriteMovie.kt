package com.oguzhan.karnavalcase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorite_movie_table")
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieId: Long,
)
