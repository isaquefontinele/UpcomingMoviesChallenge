package com.arctouch.codechallenge.util

import android.content.Context
import android.net.ConnectivityManager
import com.arctouch.codechallenge.api.TmdbApi

object Utils {

    fun getPosterLink(imagePath: String, size: String): String {
        return TmdbApi.IMAGES_BASE_URL + size + imagePath
    }

    fun getIMDbLink(imdbLink: String): String {
        return "http://www.imdb.com/title/$imdbLink"
    }

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}