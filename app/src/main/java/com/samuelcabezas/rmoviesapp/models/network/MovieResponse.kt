package com.samuelcabezas.rmoviesapp.models.network

import com.samuelcabezas.rmoviesapp.models.entity.Movie

data class MovieResponse(
        var page: Int?,
        var total_results: Int?,
        var total_pages: Int?,
        var results: List<Movie>
) {
    fun getMovieResults(): List<Movie> {
        return results
    }

}