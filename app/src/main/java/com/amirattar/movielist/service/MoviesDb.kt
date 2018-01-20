package com.amirattar.movielist.service

import com.amirattar.movielist.UpcomingMovies
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object MoviesDb {
    const val API_KEY = "get your own api key, chumps"
    const val IMAGES_BASE_URL = "http://image.tmdb.org/t/p/"
    const val IMAGES_THUMBNAIL_SIZE = "w185"
    const val IMAGES_POSTER_SIZE = "w342"
    private val service: MoviesApi
    init {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        service = retrofit.create(MoviesApi::class.java)
    }

    fun getMovies(page: Int) : Call<UpcomingMovies> = service.getUpcomingMovies(page)

    fun formatImagePath(id: String?,type: ImageType) = id?.let {  MoviesDb.IMAGES_BASE_URL+type.path+it}
}

enum class ImageType(val path: String){
    THUMBNAIL(MoviesDb.IMAGES_THUMBNAIL_SIZE),POSTER(MoviesDb.IMAGES_POSTER_SIZE)
}