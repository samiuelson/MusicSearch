package me.urbanowicz.samuel.tooplooxmusic.task

import android.support.annotation.VisibleForTesting
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalRepository
import me.urbanowicz.samuel.tooplooxmusic.extensions.levenshteinDistanceTo

/**
 * The Task responsible for performing Fuzzy search algorithm on LocalRepository of songs
 * https://en.wikipedia.org/wiki/Approximate_string_matching
 */
class SearchLocalSongsTask(@VisibleForTesting val localRepository: LocalRepository): Task.SingleParam<String, Song> {

    override fun execute(searchQuery: String): Flowable<Song> {
        return Flowable.fromIterable(localRepository.get())
                .sorted( { song1, song2 ->
                    val distanceToSong1 =
                            minOf(searchQuery.levenshteinDistanceTo(song1.getSongName()), searchQuery.levenshteinDistanceTo(song1.getArtistName()))
                    val distanceToSong2 =
                            minOf(searchQuery.levenshteinDistanceTo(song2.getSongName()), searchQuery.levenshteinDistanceTo(song2.getArtistName()))

                    // the lesser the distance the bigger similarity of song to given search query
                    distanceToSong2 - distanceToSong1
                } )
                // limit search results to the 15 best matches
                .take(15)

    }
}