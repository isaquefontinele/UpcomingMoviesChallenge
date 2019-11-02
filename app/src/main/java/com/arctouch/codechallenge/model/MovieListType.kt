package com.arctouch.codechallenge.model

import android.content.Context
import com.arctouch.codechallenge.R

enum class MovieListType {
    POPULAR, UPCOMING, PLAYING_NOW;

    companion object {
        fun getFormattedTitle(context: Context, type: MovieListType) : String {
            return when (type) {
                POPULAR -> {
                    context.getString(R.string.movie_title_popular)
                }
                UPCOMING -> {
                    context.getString(R.string.movie_title_upcoming)
                }
                PLAYING_NOW -> {
                    context.getString(R.string.movie_title_now_playing)
                }
            }
        }
    }

}