package com.oguzhan.karnavalcase.db

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import com.oguzhan.karnavalcase.model.FavoriteMovie


@Entity
@Database(entities = [FavoriteMovie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            makeDatabase(context).also {
                instance = it
            }
        }
        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }
}