package com.arctouch.codechallenge.ui

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.WebService
import com.arctouch.codechallenge.data.Cache
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MovieListType
import com.arctouch.codechallenge.repositories.MovieRepository
import com.getbase.floatingactionbutton.FloatingActionsMenu
import kotlinx.android.synthetic.main.home_activity.*

class HomePresenter (var homeView: HomeActivity) {

    val webService = WebService()
    private var currentPage: Int = 1
    private var currentQuery: String? = ""
    private var currentListType: MovieListType = MovieListType.POPULAR
    private var moviesList: ArrayList<Movie> = arrayListOf()
    private var moviesRepository = MovieRepository(homeView)

    fun getGenres() {
        homeView.showProgressBar()
        moviesRepository.getGenres({
            homeView.hideProgressBar()
            Cache.cacheGenres(it)
        }, {
            homeView.showError()
        })

    }

    private fun getUpcomingMovies(page: Int) {
        homeView.showProgressBar()
        moviesRepository.getUpcomingMovies(page, {
            moviesList.addAll(it)
            homeView.showList(moviesList)
            homeView.hideProgressBar()
        }, {
            homeView.showError()
        })
    }

    fun getPopularMovies(page: Int) {
        homeView.showProgressBar()
        moviesRepository.getPopularMovies(page, {
            moviesList.addAll(it)
            homeView.showList(moviesList)
            homeView.hideProgressBar()
        }, {
            homeView.showError()

        })
    }

    private fun getPlayingNowMovies(page: Int) {
        homeView.showProgressBar()
        moviesRepository.getPlayingNowMovies(page, {
            moviesList.addAll(it)
            homeView.showList(moviesList)
            homeView.hideProgressBar()
        }, {
            homeView.showError()
        })
    }

    private fun getMoviesBySearch(query: String, page: Int) {
        homeView.showProgressBar()
        moviesRepository.getMoviesBySearch(query, page, {
            moviesList.addAll(it)
            homeView.showList(moviesList)
            homeView.hideProgressBar()
        }, {
            homeView.showError()
        })
    }

    fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!homeView.progressBarIsVisible()) {
                    if (!recyclerView!!.canScrollVertically(1)) {
                        currentPage++
                        if (currentQuery!!.isNotEmpty()) {
                            updateMoviesListWithSearch(currentQuery!!, currentPage)
                        } else {
                            updateMoviesList(currentListType, currentPage)
                        }
                    }
                }
            }
        })
    }

    private fun updateMoviesList(currentList: MovieListType, currentPage: Int) {
        when (currentList) {
            MovieListType.POPULAR -> {
                getPopularMovies(currentPage)
            }
            MovieListType.UPCOMING -> {
                getUpcomingMovies(currentPage)
            }
            MovieListType.PLAYING_NOW -> {
                getPlayingNowMovies(currentPage)
            }
        }
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
                if (newText.isNotEmpty()) {
                    updateMoviesListWithSearch(newText, currentPage)
//                    moviesListTitle.setVisibility(View.INVISIBLE)
                } else {
                    updateMoviesList(currentListType, currentPage)
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


    fun fabOnClick(newListType: MovieListType, menu: FloatingActionsMenu) {
        if (currentListType != newListType) {
            homeView.updateTitle(newListType)
            currentListType = newListType
            resetListParameters()
            updateMoviesList(newListType, currentPage)
        }
        menu.collapse()
    }
}