package me.urbanowicz.samuel.tooplooxmusic.data.remote

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApi {

    @GET("search")
    fun getSongs(@Query("term") query: String, @Query("entity") type: String): Flowable<ItunesSongsResponse>
}