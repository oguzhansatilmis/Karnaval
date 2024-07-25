package com.oguzhan.karnavalcase.model

import com.google.gson.annotations.SerializedName
data class Movie(

    val id: Long,
    val title: String,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("poster_path")
    val posterPath: String,
    val overview: String,
    @SerializedName("vote_count")
    val voteCount: Long,
    var isFavorite:Boolean = false

)
