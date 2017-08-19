package me.urbanowicz.samuel.tooplooxmusic.task

import android.support.annotation.VisibleForTesting
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalRepository
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalSong
import me.urbanowicz.samuel.tooplooxmusic.extensions.levenshteinDistanceTo


/**
 * The Task responsible for performing Fuzzy search algorithm on LocalRepository of songs
 * https://en.wikipedia.org/wiki/Approximate_string_matching
 */
class SearchLocalSongsTask(@VisibleForTesting val localRepository: LocalRepository): Task.SingleParam<String, LocalSong> {

    override fun execute(searchQuery: String): Flowable<LocalSong> {
        return Flowable.fromIterable(localRepository.get())
                .subscribeOn(Schedulers.io())
                .sorted( { (songName1, artist1), (songName2, artist2) ->
                    val distanceToSong1 =
                            minOf(searchQuery.levenshteinDistanceTo(songName1), searchQuery.levenshteinDistanceTo(artist1))
                    val distanceToSong2 =
                            minOf(searchQuery.levenshteinDistanceTo(songName2), searchQuery.levenshteinDistanceTo(artist2))

                    // the lesser the distance the bigger similarity of song to given search query
                    distanceToSong2 - distanceToSong1
                } )
                // limit search results to the 15 best matches
                .take(15)

    }
}