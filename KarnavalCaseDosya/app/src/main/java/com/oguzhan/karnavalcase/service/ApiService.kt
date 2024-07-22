package com.oguzhan.karnavalcase.service

import com.oguzhan.karnavalcase.model.Movie
import com.oguzhan.karnavalcase.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("/3/movie/{category}")
    suspend fun getMovies(
        @Path("category") category : String,
        @Query("page") page : Int,
        @Query("api_key") apiKey : String = "9396f10ab6b6234b67c4ed3a9752666d"
    ) : Response<MovieResponse>



    @GET("/3/movie/{id}")
    suspend fun getMovieById(
        @Path("id") id : Int = 1159518,
        @Query("api_key") apiKey : String = "9396f10ab6b6234b67c4ed3a9752666d",
      //  @Query("append_to_response") appendToVideos: String = "videos"
    ) : Response<Movie>


    /*
        @GET("/3/search/multi")
    suspend fun getSearch(
        @Query("query") query: String,
        @Query("api_key") apiKey : String = API_KEY
    ) : Response<DisplayResponse>


     */



}





