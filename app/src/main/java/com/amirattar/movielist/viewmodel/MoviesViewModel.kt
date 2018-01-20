package com.amirattar.movielist.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.amirattar.movielist.Movie
import com.amirattar.movielist.UpcomingMovies
import com.amirattar.movielist.service.MoviesDb
import retrofit2.Call
import retrofit2.Callback

/**
 * Created by pantos27 on 09/12/17.
 */
private const val TAG = "MoviesViewModel"
class MoviesViewModel: ViewModel(){
    val movies = MutableLiveData<ArrayList<Movie>>()

    init {
       getPage(1)
    }

    private fun getPage(page: Int){
        MoviesDb.getMovies(page).enqueue(object : Callback<UpcomingMovies> {
            override fun onFailure(call: Call<UpcomingMovies>?, t: Throwable?) {
                Log.d(TAG,"onFailure: ")
                movies.postValue(movies.value)
            }

            override fun onResponse(call: Call<UpcomingMovies>?, response: retrofit2.Response<UpcomingMovies>?) {
                Log.d(TAG,"onResponse: ")

                response?.let {
                    if(it.isSuccessful){
                        it.body()?.let { upcomingMovies->
                            Log.d(TAG, "Received page ${upcomingMovies.page} of ${upcomingMovies.total_pages} with ${upcomingMovies.results.size} movies")
                            if (page == 1){
                                movies.postValue(ArrayList(upcomingMovies.results))
                                for (i in 2..upcomingMovies.total_pages) getPage(i)
                            }else{
                                Log.d(TAG,"adding ${upcomingMovies.results.size} to ${movies.value?.size}")
                                movies.value?.addAll(upcomingMovies.results)
                                movies.value = movies.value
                            }
                        }
                    }else{
                        Log.d(TAG,"Call unsuccessful")
                        movies.postValue(movies.value)
                    }
                }
            }

        })
    }
}

