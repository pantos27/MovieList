package com.amirattar.movielist

import java.io.Serializable
import java.util.*

/**
 * Created by pantos27 on 08/12/17.
 */

data class UpcomingMovies(val page: Int, val results: List<Movie>, val dates: Range, val total_pages: Int, val total_results: Int)

data class Range(val maximum: String,val minimum: String)

data class Movie(val poster_path: String?, val adult: Boolean, val overview: String, val release_date: String, val id: Int, val title: String,val original_language: String): Serializable{

    val language: String get() = Locale(original_language).displayLanguage


}

fun sortMovies(movies: ArrayList<Movie>) = ArrayList(movies.distinctBy { it.id }.sortedByDescending { it.release_date })