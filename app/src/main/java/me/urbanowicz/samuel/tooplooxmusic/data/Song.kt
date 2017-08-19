package me.urbanowicz.samuel.tooplooxmusic.data

import java.util.*

/**
 * Interface unifying LocalSong and RemoteSong classes
 */
interface Song {
    fun getReleaseDate(): Date
    fun getArtistName(): String
    fun getSongName(): String
}