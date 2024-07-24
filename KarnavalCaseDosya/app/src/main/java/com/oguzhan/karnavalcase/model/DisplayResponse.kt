package com.oguzhan.karnavalcase.model

data class DisplayResponse(
    var page : Int,
    var results : MutableList<Movie>,
)
