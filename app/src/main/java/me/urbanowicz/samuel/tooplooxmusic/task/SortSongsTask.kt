package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.screen.search.SortingType

class SortSongsTask: Task.DoubleParam<Flowable<Song>, SortingType, Song> {

    override fun execute(songsStream: Flowable<Song>, sortType: SortingType): Flowable<Song> {
        if (SortingType.DEFAULT == sortType) {
            return songsStream
        } else {
            return songsStream.sorted(getComparator(sortType))
        }
    }

    private fun getComparator(sortType: SortingType): (s1: Song, s2: Song) -> Int {
        when(sortType) {
            SortingType.BY_SONG -> return { s1, s2 -> s1.getSongName().compareTo(s2.getSongName(), true) }
            SortingType.BY_ARTIST -> return { s1, s2 -> s1.getArtistName().compareTo(s2.getArtistName(), true) }
            SortingType.BY_DATE -> return { s1, s2 -> s1.getReleaseDate().compareTo(s2.getReleaseDate()) }
            SortingType.DEFAULT -> return { s1, s2 -> 0 }
        }
    }

}