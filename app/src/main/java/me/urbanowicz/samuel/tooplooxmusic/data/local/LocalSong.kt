package me.urbanowicz.samuel.tooplooxmusic.data.local

import com.google.gson.annotations.SerializedName
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import java.util.*

data class LocalSong(@SerializedName("LocalSong Clean") val song: String,
                     @SerializedName("ARTIST CLEAN") val artist: String,
                     @SerializedName("Release Year") val year: Int) : Song {

    private val calendar = lazy {
        Calendar.getInstance()
    }

    override fun getReleaseDate(): Date {
        calendar.value.set(Calendar.YEAR, year)
        return calendar.value.time
    }

    override fun getArtistName(): String {
        return artist
    }

    override fun getSongName(): String {
        return song
    }
}