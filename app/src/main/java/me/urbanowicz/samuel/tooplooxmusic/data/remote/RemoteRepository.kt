package me.urbanowicz.samuel.tooplooxmusic.data.remote

import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import me.urbanowicz.samuel.tooplooxmusic.data.Repository
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RemoteRepository: Repository.Queryable<Song> {
    val api: Lazy<ItunesApi>

    init {
        api = lazy {
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://itunes.apple.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
            retrofit.create(ItunesApi::class.java)
        }
    }

    override fun get(query: String): Flowable<Song> {
        return api.value.getSongs(query, "song")
                .subscribeOn(Schedulers.io())
                .flatMap { response -> Flowable.fromIterable(response.songs)}
    }
}