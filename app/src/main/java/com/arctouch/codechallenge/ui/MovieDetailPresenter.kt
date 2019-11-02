package com.arctouch.codechallenge.ui

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.WebService
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.repositories.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailPresenter (var movieDetailsView: MovieDetailActivity) {

    private var moviesRepository = MovieRepository(movieDetailsView)

    fun loadMovieData(movieId: Int) {

        moviesRepository.getMovie(movieId, { movie ->
            movieDetailsView.hideProgressBar()
            movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(movie.id) == true })
            movieDetailsView.showMovieDetails(movie)
        }, {
            movieDetailsView.showError()
        })
    }
}