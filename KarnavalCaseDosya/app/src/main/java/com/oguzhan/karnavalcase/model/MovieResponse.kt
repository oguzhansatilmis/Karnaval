package com.oguzhan.karnavalcase.model

import com.google.gson.annotations.SerializedName


data class MovieResponse(
    var page : Int,
    var results : List<Movie>,
    @SerializedName("total_pages")
    var totalPages : Int,
    @SerializedName("total_results")
    var totalResults : Int
)
