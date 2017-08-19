package me.urbanowicz.samuel.tooplooxmusic.data.local

import com.google.gson.annotations.SerializedName

data class LocalSong(@SerializedName("LocalSong Clean") val songName: String,
                     @SerializedName("ARTIST CLEAN") val artist: String,
                     @SerializedName("Release Year") val year: String)