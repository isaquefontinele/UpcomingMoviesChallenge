package com.arctouch.codechallenge.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.model.MovieListType
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeInterface {

    private lateinit var presenter: HomePresenter
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        presenter = HomePresenter(this)

        setupViews()
        setupFab()
        presenter.getGenres()
        presenter.getPopularMovies(1)
        presenter.setupRecyclerView(recyclerView)
        presenter.setupSearchBar()
    }

    private fun setupViews() {
        movies_list_type.setText(R.string.movie_title_popular)
        homeAdapter = HomeAdapter(ArrayList())
        recyclerView.adapter = homeAdapter
    }

    private fun setupFab() {
        fab_action_popular.setOnClickListener { presenter.fabOnClick(MovieListType.POPULAR, fab_menu) }
        fab_action_playing_now.setOnClickListener { presenter.fabOnClick(MovieListType.PLAYING_NOW, fab_menu) }
        fab_action_upcoming.setOnClickListener { presenter.fabOnClick(MovieListType.UPCOMING, fab_menu) }
    }

    override fun showList(moviesWithGenres: List<Movie>) {
        recyclerView.visibility = View.VISIBLE
        movies_list_type.visibility = View.VISIBLE
        error_loading.visibility = View.GONE
        homeAdapter.updateList(moviesWithGenres)
    }

    override fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    override fun progressBarIsVisible() : Boolean {
        return progressBar.visibility == View.VISIBLE
    }

    override fun updateTitle(newType: MovieListType) {
        movies_list_type.text = MovieListType.getFormattedTitle(this, newType)
    }

    override fun showError() {
        recyclerView.visibility = View.GONE
        movies_list_type.visibility = View.GONE
        error_loading.visibility = View.VISIBLE
        hideProgressBar()
    }
}
