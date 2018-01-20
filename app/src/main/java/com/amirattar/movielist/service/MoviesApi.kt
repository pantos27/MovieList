package com.amirattar.movielist.service

import com.amirattar.movielist.UpcomingMovies
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by pantos27 on 08/12/17.
 */
interface MoviesApi {
    @GET("3/movie/upcoming")
    fun getUpcomingMovies(@Query("page") page: Int,@Query("api_key") api_key: String = MoviesDb.API_KEY) : Call<UpcomingMovies>
}