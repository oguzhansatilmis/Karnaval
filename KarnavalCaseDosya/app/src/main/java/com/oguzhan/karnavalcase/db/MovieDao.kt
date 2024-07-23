package com.oguzhan.karnavalcase.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oguzhan.karnavalcase.model.FavoriteMovie
import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.MovieResponse


@Dao
interface MovieDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie)

    @Query("SELECT * FROM favorite_movie_table")
    suspend fun getFavoriteMoviesList(): List<FavoriteMovie>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_movie_table WHERE movieId = :movieId LIMIT 1)")
    suspend fun isFavoriteMovie(movieId: Long): Boolean


    @Query("DELETE FROM favorite_movie_table WHERE movieId = :movieId")
    suspend fun deleteFavoriteMovieById(movieId: Long)

}