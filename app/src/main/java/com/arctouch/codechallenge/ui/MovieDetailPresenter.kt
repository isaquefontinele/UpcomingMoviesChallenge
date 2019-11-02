package com.arctouch.codechallenge.ui

import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.WebService
import com.arctouch.codechallenge.data.Cache
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailPresenter (var movieDetailsView: MovieDetailsInterface) {

    private var webService = WebService()

    fun loadMovieData(movieId: Int) {
        webService.api.movie(movieId, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    val movie = it
                    movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    movieDetailsView.showMovieDetails(movie)
                }
    }
}