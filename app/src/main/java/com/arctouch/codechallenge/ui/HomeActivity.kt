package com.arctouch.codechallenge.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import kotlinx.android.synthetic.main.home_activity.*

class HomeActivity : AppCompatActivity(), HomeInterface {

    private lateinit var presenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)

        presenter = HomePresenter(this)
        presenter.getGenres()
    }

    override fun showList(moviesWithGenres: List<Movie>) {
        recyclerView.adapter = HomeAdapter(this, moviesWithGenres)
        progressBar.visibility = View.GONE
    }
}
