package com.arctouch.codechallenge.ui

import com.arctouch.codechallenge.model.Movie

interface MovieDetailsInterface {
    fun showMovieDetails(movie: Movie)

    fun showProgressBar()

    fun hideProgressBar()

    fun showError()
}