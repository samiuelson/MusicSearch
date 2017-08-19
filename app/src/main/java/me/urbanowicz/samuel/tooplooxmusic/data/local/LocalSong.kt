package me.urbanowicz.samuel.tooplooxmusic.data.local

import com.google.gson.annotations.SerializedName
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import java.util.*

data class LocalSong(@SerializedName("Song Clean") val song: String,
                     @SerializedName("ARTIST CLEAN") val artist: String,
                     @SerializedName("Release Year") val year: Int) : Song {

    override fun getReleaseDate(): Date {
        if (year != Int.MIN_VALUE) {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, year)
            return calendar.time
        } else {
            return Date(0)
        }
    }

    override fun getArtistName(): String {
        return artist
    }

    override fun getSongName(): String {
        return song
    }
}