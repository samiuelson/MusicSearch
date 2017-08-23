package me.urbanowicz.samuel.tooplooxmusic.data.remote

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

class RemoteSongTest {
    @Test
    fun getReleaseDate() {
        // when is created
        val s = RemoteSong("Bob Marley", "1980-03-01T08:00:00Z", "Uprising")

        // then
        val date = s.getReleaseDate().time

        assertEquals(SimpleDateFormat("yyyy").format(date), "1980")
        assertEquals(SimpleDateFormat("MM").format(date), "03")
        assertEquals(SimpleDateFormat("dd").format(date), "01")
    }

}