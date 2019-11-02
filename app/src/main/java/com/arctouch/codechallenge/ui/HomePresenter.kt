package com.arctouch.codechallenge.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.api.WebService
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.home_activity.*

class HomePresenter (var homeView: HomeActivity) {

    val webService = WebService()
    private var currentPage: Int = 1
    private var currentQuery: String? = ""
    private var currentList: String = "upcoming"
    private var moviesList: ArrayList<Movie> = arrayListOf()

    fun getGenres() {
//        LoadingAlert.show(homeView)
        homeView.showProgressBar()
        webService.api.genres(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    homeView.hideProgressBar()
                    Cache.cacheGenres(it.genres)
                }
    }

    fun getUpcomingMovies(page: Int) {
        homeView.showProgressBar()
        webService.api.upcomingMovies(TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                    val moviesWithGenres = it.results.map { movie ->
                        movie.copy(genres = Cache.genres.filter { movie.genreIds?.contains(it.id) == true })
                    }
                    moviesList.addAll(moviesWithGenres)
                    homeView.showList(moviesList)
                    homeView.hideProgressBar()
                }
    }

    fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (homeView.progressBar.visibility != View.VISIBLE) {
                    if (!recyclerView!!.canScrollVertically(1)) {
                        currentPage++
                        if (currentQuery!!.isNotEmpty()) {
                            updateMoviesListWithSearch(currentQuery!!, currentPage)
                        } else {
                            updateMoviesList(currentList, currentPage)
                        }
                    }
                }
            }
        })
    }

    private fun updateMoviesList(currentList: String, currentPage: Int) {
        getUpcomingMovies(currentPage)
    }

    private fun updateMoviesListWithSearch(currentQuery: String, currentPage: Int) {

    }
}