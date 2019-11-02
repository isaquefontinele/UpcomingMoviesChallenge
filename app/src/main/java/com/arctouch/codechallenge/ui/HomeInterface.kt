package com.arctouch.codechallenge.ui

import com.arctouch.codechallenge.model.Movie

interface HomeInterface {
    fun showList(moviesWithGenres: List<Movie>)

    fun showProgressBar()

    fun hideProgressBar()
}