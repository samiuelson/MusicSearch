package me.urbanowicz.samuel.tooplooxmusic.data.local

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import me.urbanowicz.samuel.tooplooxmusic.data.Repository
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.extensions.fromJson

class LocalRepository(private val json: String): Repository.Simple<Song> {
    private val TAG = "LocalRepository"

    private val songs: Lazy<Collection<Song>>

    init {
        songs = lazy {
            val gson =
                GsonBuilder().registerTypeAdapter(Int::class.java, JsonDeserializer<Int> { element, _, _ ->
                    var result: Int = Int.MIN_VALUE
                    element?.let {
                        if (!element.isJsonPrimitive) {
                            result = Int.MIN_VALUE
                        } else {
                            try {
                                result = element.asJsonPrimitive.asNumber.toInt()
                            } catch (e: NumberFormatException) {
                                Log.d(TAG, "Received bad number format. Ignoring \"${element.asString}\" value.")
                            }
                        }
                    }
                    result
                }).create()
            val songs: Collection<LocalSong> = gson.fromJson(json)
            songs
        }
    }

    override fun get(): Collection<Song> {
        return songs.value
    }

}