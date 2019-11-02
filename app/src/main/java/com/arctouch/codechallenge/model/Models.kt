package com.arctouch.codechallenge.model

import com.squareup.moshi.Json

data class GenreResponse(val genres: List<Genre>)

data class Genre(val id: Int, val name: String)

data class UpcomingMoviesResponse(
    val page: Int,
    val results: List<Movie>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String?,
    val genres: List<Genre>?,
    @Json(name = "genre_ids") val genreIds: List<Int>?,
    @Json(name = "poster_path") val posterPath: String? = "",
    @Json(name = "backdrop_path") val backdropPath: String? = "",
    @Json(name = "release_date") val releaseDate: String?,
    @Json(name = "vote_average") val voteAverage: Double = 0.toDouble(),
    @Json(name = "popularity") val popularity: Double = 0.toDouble(),
    @Json(name = "original_title") val originalTitle: String? = null,
    @Json(name = "original_language") val originalLanguage: String? = null,
    @Json(name = "homepage") val homepage: String? = null,
    @Json(name = "imdb_id") val imdbId: String? = null
)
