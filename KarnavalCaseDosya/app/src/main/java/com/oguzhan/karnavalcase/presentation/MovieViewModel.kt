package com.oguzhan.karnavalcase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oguzhan.karnavalcase.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    fun fonksiyon(){
        viewModelScope.launch {

            val result = repository.getMovie()

            println("Burası view model ")


            result?.let { result ->

                if (result.isSuccessful) {

                   val data = result


                println(data)




                } else {
                    println("result başarılı değil")
                    println(result.message())
                    println(result.code())
                }
            }
        }
    }


    fun fonksiyon2(){
        viewModelScope.launch {

            val result = repository. getMoviesById()

            println("Burası view model ")


            result?.let { result ->

                if (result.isSuccessful) {

                    val data = result.body()




                        println(data)


                } else {
                    println("result başarılı değil")
                    println(result.message())
                    println(result.code())
                }
            }
        }
    }
}