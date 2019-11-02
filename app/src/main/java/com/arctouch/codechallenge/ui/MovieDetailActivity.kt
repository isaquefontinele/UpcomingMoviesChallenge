package com.arctouch.codechallenge.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MOVIE_ID
import kotlinx.android.synthetic.main.activity_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailsInterface {

    private var instanceState: Bundle? = null
    private lateinit var presenter: MovieDetailPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.instanceState = savedInstanceState
        setContentView(R.layout.activity_movie_detail)

        setSupportActionBar(detail_toolbar)

        // Show the Up button in the action bar.
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getIntExtra(MOVIE_ID, 0)

        presenter = MovieDetailPresenter(this)
        presenter.loadMovieData(movieId)
    }


    override fun showMovieDetails(movie: Movie) {
        if (movie != null) {
            movieName.text = movie.originalTitle
            movieID.text = movie.id.toString()
            release_date.text = movie.releaseDate
            genres.text = movie.genres.toString()
            rating.text = movie.voteAverage.toString()

            if (movie.overview != null && movie.overview.isNotEmpty()) {
                movie_sinopse.text = movie.overview
            } else {
                movie_sinopse.setText(R.string.overview_not_found)
            }

            setUpLinks(movie)
        } else {
            movie_details_not_found.visibility = View.VISIBLE
            details_body.visibility = View.GONE
        }
    }

    private fun setUpLinks(movie: Movie) {

        if (movie.imdbId != null && movie.imdbId.isNotEmpty()) {
            imdb_link.text = "http://www.imdb.com/title/${movie.imdbId}"
        } else {
            imdb_line.visibility = View.GONE
        }

        if (movie.homepage != null && movie.homepage.isNotEmpty()) {
            homepage.text = movie.homepage
        } else {
            homepage_line.visibility = View.GONE
        }

        if (imdb_line.visibility == View.GONE && homepage_line.visibility == View.GONE) {
            title_links.visibility = View.GONE
        }
    }
}