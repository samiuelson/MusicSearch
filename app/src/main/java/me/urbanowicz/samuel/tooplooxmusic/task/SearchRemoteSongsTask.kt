package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.data.remote.RemoteRepository

class SearchRemoteSongsTask(val remoteRepository: RemoteRepository): Task.SingleParam<String, Song> {

    override fun execute(query: String): Flowable<Song> {
        return remoteRepository.get(query)
    }

}