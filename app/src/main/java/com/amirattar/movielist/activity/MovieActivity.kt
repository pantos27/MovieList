package com.amirattar.movielist.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.amirattar.movielist.Movie
import com.amirattar.movielist.R
import com.amirattar.movielist.service.ImageType
import com.amirattar.movielist.service.MoviesDb
import com.bumptech.glide.Glide

import kotlinx.android.synthetic.main.activity_movie.*
import kotlinx.android.synthetic.main.content_movie.*
import java.util.*

class MovieActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_MOVIE = "movie_extra"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { _ ->
            finish()
        }

        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as? Movie

        if (movie !=null)
            setContent(movie)
        else
            finish()
    }

    private fun setContent(movie: Movie) {
        Glide.with(this)
                .load(MoviesDb.formatImagePath(movie.poster_path, ImageType.POSTER))
                .fallback(R.drawable.ic_movie_black_24dp)
                .into(imageView)
        textOverview.text = movie.overview
        textRelease.text = movie.release_date
        textTitle.text = movie.title
        textLang.text = movie.language
    }

}
