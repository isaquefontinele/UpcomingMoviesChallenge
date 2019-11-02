package com.arctouch.codechallenge.ui

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.arctouch.codechallenge.R
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

    fun getMoviesBySearch(query: String, page: Int) {
        homeView.showProgressBar()
        webService.api.getMoviesListBySearch(TmdbApi.API_KEY, query, page, TmdbApi.DEFAULT_LANGUAGE)
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
        getMoviesBySearch(currentQuery, currentPage)
    }

    fun setupSearchBar() {
        currentQuery = ""
        homeView.search_bar.queryHint = homeView.resources.getString(R.string.search_hint)
        homeView.search_bar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                resetListParameters()
                currentQuery = newText
                if (!newText.isEmpty()) {
                    updateMoviesListWithSearch(newText, currentPage)
//                    moviesListTitle.setVisibility(View.INVISIBLE)
                } else {
                    updateMoviesList("", currentPage)
//                    moviesListTitle.setVisibility(View.VISIBLE)
                }
                return false
            }
        })
        homeView.search_bar.setIconifiedByDefault(false)
        homeView.search_bar.setBackgroundColor(ContextCompat.getColor(homeView, R.color.search_background))
        homeView.recyclerView.requestFocus()
    }

    private fun resetListParameters() {
        moviesList.clear()
        currentPage = 1
    }
}