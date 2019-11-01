package com.arctouch.codechallenge.home

import android.os.Bundle
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.base.BaseActivity
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MOVIE_ID
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : BaseActivity() {

    private var mMovie: Movie? = null
    private var instanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.instanceState = savedInstanceState
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        loadMovieData()
    }

    private fun loadMovieData() {
        val movieId = intent.getIntExtra(MOVIE_ID, 0)
        api.movie(movieId, TmdbApi.API_KEY, TmdbApi.DEFAULT_LANGUAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loadViews(it)
                }
    }

    private fun loadViews(movie: Movie?) {

    }
}