package me.urbanowicz.samuel.tooplooxmusic.data.local

import com.google.gson.Gson
import me.urbanowicz.samuel.tooplooxmusic.data.Repository
import me.urbanowicz.samuel.tooplooxmusic.extensions.fromJson

// TODO: consider replacing json: String property with InputStream in case of OOM
class LocalRepository(private val json: String): Repository<LocalSong> {

    override fun get(): Collection<LocalSong> {
        return Gson().fromJson(json)
    }

}