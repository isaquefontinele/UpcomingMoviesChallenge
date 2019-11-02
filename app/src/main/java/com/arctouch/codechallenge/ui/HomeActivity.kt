package com.arctouch.codechallenge.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeInterface {

    private lateinit var presenter: HomePresenter
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        presenter = HomePresenter(this)

        setupViews()
        presenter.getGenres()
        presenter.getUpcomingMovies(1)
        presenter.setupRecyclerView(recyclerView)
        presenter.setupSearchBar()

    }

    private fun setupViews() {
        homeAdapter = HomeAdapter(ArrayList())
        recyclerView.adapter = homeAdapter
    }

    override fun showList(moviesWithGenres: List<Movie>) {
        homeAdapter.updateList(moviesWithGenres)
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}
