package me.urbanowicz.samuel.tooplooxmusic.data

import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalSong

class Data {
    object Mocks {
        fun provideSongs(): Collection<LocalSong> {
            val songs = ArrayList<LocalSong>()
            songs.add(LocalSong("Happy", "Pharel Williams", 2013))
            songs.add(LocalSong("Don't Worry, Be Happy", "Bobby McFerrin", 1988))
            songs.add(LocalSong("Redemption Song", "Bob Marley", 1980))
            return songs
        }
    }
}