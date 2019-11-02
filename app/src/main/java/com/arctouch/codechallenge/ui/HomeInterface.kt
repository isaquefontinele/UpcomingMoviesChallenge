package com.arctouch.codechallenge.ui

import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MovieListType

interface HomeInterface {
    fun showList(moviesWithGenres: List<Movie>)

    fun showProgressBar()

    fun hideProgressBar()

    fun progressBarIsVisible() : Boolean

    fun updateTitle(newType: MovieListType)

    fun showError()
}