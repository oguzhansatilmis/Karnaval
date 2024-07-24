package com.oguzhan.karnavalcase.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhan.karnavalcase.model.DisplayResponse
import com.oguzhan.karnavalcase.model.FavoriteMovie
import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.MovieResponse
import com.oguzhan.karnavalcase.model.Resource
import com.oguzhan.karnavalcase.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    //Encapsulation
    private val _popularMovies = MutableLiveData<Resource<MovieResponse>>(Resource.Loading())
     val popularMovies : LiveData<Resource<MovieResponse>> = _popularMovies


    private val _searchMovies = MutableLiveData<Resource<DisplayResponse>>(Resource.Loading())
    val searchMovies : LiveData<Resource<DisplayResponse>> = _searchMovies

    fun getPopularMovies(page: Int){
        viewModelScope.launch {
            val popularMovies = repository.getMovie(page)
            val favoriteMovies = repository.getFavoriteMoviesList()
            popularMovies?.let { popularMovies ->
                if (popularMovies.isSuccessful){
                    val popularMoviesBody = popularMovies.body()
                    popularMoviesBody?.let { movie->
                        movie.results.forEach { movie->
                            val isFavorite = favoriteMovies.any { it.movieId == movie.id }
                            movie.isFavorite = isFavorite
                        }
                        _popularMovies.value = Resource.Success(movie)
                    }
                }
                else{
                    _popularMovies.value = Resource.Error(message = "Network Error")
                }
            }

        }
    }
    fun searchMovie(query: String){
        viewModelScope.launch {
            val searchMovies = repository.getSearchMovies(query)
            searchMovies?.let {
                if (searchMovies.isSuccessful){
                    val searchMoviesBody = searchMovies.body()
                    searchMoviesBody?.let { searchMovie->
                        _searchMovies.value = Resource.Success(searchMovie)
                    }
                }
                else{
                    _searchMovies.value = Resource.Error(message = "Network Error")
                }
            }
        }
    }
}