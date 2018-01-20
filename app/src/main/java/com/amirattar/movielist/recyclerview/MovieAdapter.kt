package com.amirattar.movielist.recyclerview

import android.content.Intent
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amirattar.movielist.Movie
import com.amirattar.movielist.R
import com.amirattar.movielist.activity.MovieActivity
import com.amirattar.movielist.service.ImageType
import com.amirattar.movielist.service.MoviesDb
import com.bumptech.glide.Glide
import com.squareup.moshi.Moshi
import java.util.*

/**
 * Created by pantos27 on 08/12/17.
 */
class MovieAdapter(private var movieList: ArrayList<Movie>) : RecyclerView.Adapter<DefaultViewHolder>() {
    private var filteredMovies = ArrayList<Movie>()
    private var filtering = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val inflatedView : View = layoutInflater.inflate(R.layout.row_item, parent,false)
        return DefaultViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        if (filtering) {
            return filteredMovies.size
        }
        return movieList.size
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val movieRow : Movie = if (filtering) {
            filteredMovies[position]
        } else {
            movieList[position]
        }

        Glide.with(holder.itemView.context)
                .load(MoviesDb.formatImagePath(movieRow.poster_path,ImageType.THUMBNAIL))
                .fallback(R.drawable.ic_movie_black_24dp)
                .into(holder.getImage(R.id.movie_image))
        holder.setText(R.id.movie_title, movieRow.title)
        holder.setText(R.id.movie_lang, movieRow.language)
        holder.setText(R.id.movie_release, movieRow.release_date)
        holder.itemView.setOnClickListener {
            it.context.startActivity(Intent(it.context,MovieActivity::class.java).putExtra(MovieActivity.EXTRA_MOVIE,movieRow).setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION))
        }
    }

    private fun clearFilter() {
        filtering = false
        filteredMovies.clear()
    }

    fun updateMovies(movies: ArrayList<Movie>) {
        DiffUtil.calculateDiff(MovieRowDiffCallback(movies, movieList), false).dispatchUpdatesTo(this)
        movieList = movies
        clearFilter()
    }


    fun filterMovies(lang: String,date: String) {
        Log.d("Filter","lang: $lang date: $date")
        if(lang.isEmpty() && date.isEmpty()){
            updateMovies(movieList)
            return
        }
        filtering = true
        //apply filters
        val newMovies = movieList.filter { movie -> movie.release_date.contains(date) && movie.language.contains(lang) } as ArrayList<Movie>
        DiffUtil.calculateDiff(MovieRowDiffCallback(newMovies, movieList), false).dispatchUpdatesTo(this)
        filteredMovies = newMovies
    }
}