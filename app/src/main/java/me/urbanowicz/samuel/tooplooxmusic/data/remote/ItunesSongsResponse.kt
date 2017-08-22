package me.urbanowicz.samuel.tooplooxmusic.data.remote

import com.google.gson.annotations.SerializedName

class ItunesSongsResponse(@SerializedName("results") val songs: Collection<RemoteSong>) {
}