package com.oguzhan.karnavalcase.repository

import android.util.Printer
import androidx.lifecycle.LiveData
import com.oguzhan.karnavalcase.base.BaseRepository
import com.oguzhan.karnavalcase.db.MovieDao
import com.oguzhan.karnavalcase.model.FavoriteMovie
import com.oguzhan.karnavalcase.service.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
) :BaseRepository(){

    suspend fun getMovie(page:Int) = safeApiCall {
        apiService.getMovies(page)
    }
    suspend fun getMoviesById(movieId:Long) = safeApiCall {
        apiService.getMovieById(movieId)
    }


    //Database

    suspend fun insertFavoriteMovie(favoriteMovie: FavoriteMovie){

        movieDao.insertFavoriteMovie(favoriteMovie)
    }
    suspend fun getFavoriteMoviesList() :List<FavoriteMovie>{
        return movieDao.getFavoriteMoviesList()
    }

     suspend fun isFavoriteMovie(movieId:Long):Boolean{
        return movieDao.isFavoriteMovie(movieId)
    }
    suspend fun deleteFavoriteMovieById(movieId: Long){
        movieDao.deleteFavoriteMovieById(movieId)
    }


}