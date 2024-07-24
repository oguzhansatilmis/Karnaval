package com.oguzhan.karnavalcase.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhan.karnavalcase.model.FavoriteMovie
import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.Resource
import com.oguzhan.karnavalcase.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: Repository
):ViewModel() {

    private val _popularMoviesById = MutableLiveData<Resource<Movie>>(Resource.Loading())
    val popularMoviesById : LiveData<Resource<Movie>> = _popularMoviesById

    fun getPopularMovieById(movieId:Long){
        viewModelScope.launch {
            val movie = repository.getMoviesById(movieId)
            movie?.let { response->
                if (response.isSuccessful){
                    val movieBody = response.body()
                    movieBody?.let { movies->

                        _popularMoviesById.value = Resource.Success(movies)
                    }
                }
                else{
                    _popularMoviesById.value = Resource.Error("Network Error")
                }
            }
        }
    }
    fun addedToFavoriteMovie(movieId:Long){
        viewModelScope.launch {
            insertFavoriteMovie(movieId)
        }
    }
    private suspend fun insertFavoriteMovie(movieId:Long){
        val favoriteMovie = FavoriteMovie(movieId = movieId)
        repository.insertFavoriteMovie(favoriteMovie)

    }

     suspend fun isFavoriteMovie(movieId:Long):Boolean{
        return repository.isFavoriteMovie(movieId)
    }
     fun deleteFavoriteMovieById(movieId: Long){
         viewModelScope.launch {
             repository.deleteFavoriteMovieById(movieId)
         }

    }

}