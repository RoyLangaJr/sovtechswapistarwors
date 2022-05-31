package com.juniorlanga.swapistarwars.network

import com.juniorlanga.swapistarwars.models.FilmResponse
import com.juniorlanga.swapistarwars.models.PeopleResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET("people/?page/")
    suspend fun getCharacters(@Query("page") page: Int): PeopleResponse

    @GET
    suspend fun getFilm(@Url url: String): FilmResponse

}