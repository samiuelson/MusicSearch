package me.urbanowicz.samuel.tooplooxmusic.task

import android.support.annotation.VisibleForTesting
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalRepository
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalSong
import me.urbanowicz.samuel.tooplooxmusic.data.remote.RemoteRepository
import me.urbanowicz.samuel.tooplooxmusic.extensions.levenshteinDistanceTo


/**
 * The Task responsible for performing Fuzzy search algorithm on LocalRepository of songs
 * https://en.wikipedia.org/wiki/Approximate_string_matching
 */
class GetAllSongsTask(@VisibleForTesting val localRepository: LocalRepository,
                      @VisibleForTesting val remoteRepository: RemoteRepository): Task.NoParam<Song> {

    override fun execute(): Flowable<Song> {
        return Flowable.fromIterable(localRepository.get())
    }

}