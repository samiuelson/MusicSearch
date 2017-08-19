package me.urbanowicz.samuel.tooplooxmusic.data.remote

import com.google.gson.annotations.SerializedName
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import java.util.*

data class RemoteSong(@SerializedName("artistName") val artist: String,
                      val releaseDate: String,
                      val trackName: String) : Song {
    override fun getReleaseDate(): Date {
        TODO("not implemented")
    }

    override fun getArtistName(): String {
        return artist
    }

    override fun getSongName(): String {
        return trackName
    }
}