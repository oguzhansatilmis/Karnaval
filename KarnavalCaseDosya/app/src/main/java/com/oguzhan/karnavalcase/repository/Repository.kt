package com.oguzhan.karnavalcase.repository

import com.oguzhan.karnavalcase.base.BaseRepository
import com.oguzhan.karnavalcase.service.ApiService
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService
) :BaseRepository(){

    suspend fun getMovie() = safeApiCall {
        apiService.getMovies("popular",1)
    }
    suspend fun getMoviesById() = safeApiCall {
        apiService.getMovieById()
    }
}