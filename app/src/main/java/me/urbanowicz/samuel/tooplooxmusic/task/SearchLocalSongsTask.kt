package me.urbanowicz.samuel.tooplooxmusic.task

import android.support.annotation.VisibleForTesting
import io.reactivex.Flowable
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalRepository

class SearchLocalSongsTask(val localRepository: LocalRepository): Task.SingleParam<String, Song> {

    override fun execute(searchQuery: String): Flowable<Song> {
        val queryEmpty = searchQuery.isNullOrEmpty()
        val query = searchQuery.toLowerCase().trim()
        val stream: Flowable<Song>

        if (!queryEmpty) {
            stream = Flowable.fromIterable(localRepository.get())

                    .filter {song ->
                        ("${song.getSongName().toLowerCase()} ${song.getArtistName().toLowerCase()}").contains(query)
                    }
        } else {
            stream = Flowable.fromIterable(localRepository.get())
        }
        return stream
    }
}