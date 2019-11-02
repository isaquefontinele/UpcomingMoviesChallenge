package com.arctouch.codechallenge.ui

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arctouch.codechallenge.R
import com.arctouch.codechallenge.api.TmdbApi
import com.arctouch.codechallenge.model.Movie
import com.arctouch.codechallenge.util.MOVIE_ID
import com.arctouch.codechallenge.util.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_item.view.*

class HomeAdapter(private var movies: ArrayList<Movie>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(movie: Movie) {
            itemView.titleTextView.text = movie.title
            itemView.genresTextView.text = movie.genres?.joinToString(separator = ", ") { it.name }
            itemView.releaseDateTextView.text = movie.releaseDate

            if (movie.posterPath != null) {
                Picasso.with(itemView.context).load(Utils.getPosterLink(movie.posterPath,
                        TmdbApi.POSTER_SIZE_MINI)).placeholder(R.drawable.placeholder).into(itemView.posterImageView)
            }
            itemView.movieCardView.setOnClickListener {
                val intent = Intent(itemView.context, MovieDetailActivity::class.java)
                intent.putExtra(MOVIE_ID, movie.id)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(movies[position])

    fun updateList(moviesNewList: List<Movie>) {
        movies.clear()
        movies.addAll(moviesNewList)
        notifyDataSetChanged()
    }
}
