package com.amirattar.movielist.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.amirattar.movielist.Movie
import com.amirattar.movielist.R
import com.amirattar.movielist.recyclerview.MovieAdapter
import com.amirattar.movielist.sortMovies
import com.amirattar.movielist.viewmodel.MoviesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "Movies-MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.visibility = View.GONE
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recycler_view.layoutManager = LinearLayoutManager(this)

        ViewModelProviders.of(this).get(MoviesViewModel::class.java).movies.observe(this, Observer {
            it?.let { latestMovies ->
                val sortedMovies = sortMovies(latestMovies)
                if (recycler_view.adapter == null) {
                    recycler_view.adapter = MovieAdapter(sortedMovies)
                } else {
                    (recycler_view.adapter as MovieAdapter).updateMovies(sortedMovies)
                }
                recycler_view.visibility = View.VISIBLE
                recycler_view.scrollToPosition(0)
                progress.visibility = View.GONE

                setSpinners(sortedMovies)
            }
        })
    }

    private var currentLang: String? = null
    private var currentDate: String? = null

    private fun setSpinners(sortedMovies: ArrayList<Movie>) {
        //set a default value
        val languages = arrayListOf(getString(R.string.all))
        val dates = arrayListOf(getString(R.string.all))
        //get all languages
        languages.addAll(sortedMovies.distinctBy { it.original_language }.map { it.language }.sorted())
        languages.forEach { Log.d(TAG, "Lang: $it") }
        //get all release dates
        dates.addAll(sortedMovies.distinctBy { it.release_date }.map { it.release_date })
        dates.forEach { Log.d(TAG, "date: $it") }

        spinner_lang.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages)
        spinner_date.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, dates)


        spinner_lang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                currentLang = if(position==0) null else languages[position]
                filterMovies()
            }
        }

        spinner_date.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>) {
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                currentDate = if(position==0) null else dates[position]
                filterMovies()
            }
        }
    }

    fun filterMovies(){
        //data exists and a filter is selected
        if (recycler_view.adapter != null) {
            (recycler_view.adapter as MovieAdapter).filterMovies(currentLang?:"",currentDate?:"")
        }
    }
}
