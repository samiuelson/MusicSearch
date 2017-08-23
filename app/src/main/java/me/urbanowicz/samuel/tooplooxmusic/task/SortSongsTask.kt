package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.screen.search.SortType

class SortSongsTask: Task.DoubleParam<Flowable<Song>, SortType, Song> {

    override fun execute(songsStream: Flowable<Song>, sortType: SortType): Flowable<Song> {
        if (SortType.DEFAULT == sortType) {
            return songsStream
        } else {
            return songsStream.sorted(getComparator(sortType))
        }
    }

    private fun getComparator(sortType: SortType): (s1: Song, s2: Song) -> Int {
        when(sortType) {
            SortType.BY_SONG -> return { s1, s2 -> s1.getSongName().compareTo(s2.getSongName(), true) }
            SortType.BY_ARTIST -> return { s1, s2 -> s1.getArtistName().compareTo(s2.getArtistName(), true) }
            SortType.BY_DATE -> return { s1, s2 -> s1.getReleaseDate().compareTo(s2.getReleaseDate()) }
            SortType.DEFAULT -> return { s1, s2 -> 0 }
        }
    }

}