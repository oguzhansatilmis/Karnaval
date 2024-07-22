package com.oguzhan.karnavalcase.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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


    fun getPopularMovies(){

        viewModelScope.launch {
            val popularMovies = repository.getMovie()

            popularMovies?.let { response->

                if (response.isSuccessful){

                    val responseBody = response.body()

                    responseBody?.let { movies->

                        _popularMovies.value = Resource.Success(movies)

                    }
                }
                else{
                    _popularMovies.value = Resource.Error("Network Error")
                }

            }

        }


    }


}