package com.arctouch.codechallenge.repositories

import android.content.Context
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.WebService
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Genre
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieRepository (var context: Context) {

    private val webService = WebService()

    fun getUpcomingMovies(page: Int, onSuccess: (List<Movie>) -> Unit, onFailure: () -> Unit) {
        if (Utils.isConnected(context)) {
            try {
                webService.api.upcomingMovies(TmdbApi.API_KEY, page, TmdbApi.DEFAULT_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {

                            val moviesWithGenres = it.results.map { movie ->
                                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                            }
                            onSuccess(moviesWithGenres)
                        }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        } else {
            onFailure()
        }
    }

    fun getPopularMovies(page: Int, onSuccess: (List<Movie>) -> Unit, onFailure: () -> Unit) {
        if (Utils.isConnected(context)) {
            try {
                webService.api.popularMovies(TmdbApi.API_KEY, page, TmdbApi.DEFAULT_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val moviesWithGenres = it.results.map { movie ->
                                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                            }
                            onSuccess(moviesWithGenres)
                        }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        } else {
            onFailure()
        }
    }

    fun getPlayingNowMovies(page: Int, onSuccess: (List<Movie>) -> Unit, onFailure: () -> Unit) {
        if (Utils.isConnected(context)) {
            try {
                webService.api.getNowPlayingMovies(TmdbApi.API_KEY, page, TmdbApi.DEFAULT_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val moviesWithGenres = it.results.map { movie ->
                                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                            }
                            onSuccess(moviesWithGenres)
                        }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        } else {
            onFailure()
        }
    }

    fun getMoviesBySearch(query: String, page: Int, onSuccess: (List<Movie>) -> Unit, onFailure: () -> Unit) {
        if (Utils.isConnected(context)) {
            try {
                webService.api.getMoviesListBySearch(TmdbApi.API_KEY, query, page, TmdbApi.DEFAULT_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            val moviesWithGenres = it.results.map { movie ->
                                movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                            }
                            onSuccess(moviesWithGenres)
                        }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        } else {
            onFailure()
        }

    }

    fun getGenres(onSuccess: (List<Genre>) -> Unit, onFailure: () -> Unit) {
        if (Utils.isConnected(context)) {
            try {
                webService.api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            onSuccess(it.genres)
                        }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        } else {
            onFailure()
        }
    }

    fun getMovie(movieId: Int, onSuccess: (Movie) -> Unit, onFailure: () -> Unit) {
        if (Utils.isConnected(context)) {
            try {
                webService.api.movie(movieId, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            onSuccess(it)
                        }
            } catch (e: Exception) {
                e.printStackTrace()
                onFailure()
            }
        } else {
            onFailure()
        }
    }
}