package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import me.urbanowicz.samuel.tooplooxmusic.data.Data
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.screen.search.SortType
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SortSongsTaskTest {
    
    val task = SortSongsTask()
    val songsStream = Flowable.fromIterable(Data.Mocks.provideSongs() as Collection<Song>)

    @Test fun sortDefault() {
        // given
        assertNotNull(task)
        // when sort use-case invoked with SortType.DEFAULT
        val result: Flowable<Song> = task.execute(songsStream, SortType.DEFAULT)
        val testSubscriber = TestSubscriber<Song>()
        result.subscribe(testSubscriber)
        // then
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        assertTrue(testSubscriber.values() == Data.Mocks.provideSongs())
    }

    @Test fun sortSongName() {
        // given
        assertNotNull(task)
        // when sort use-case invoked with SortType.BY_SONG
        val result: Flowable<Song> = task.execute(songsStream, SortType.BY_SONG)
        val testSubscriber = TestSubscriber<Song>()
        result.subscribe(testSubscriber)
        // then songs should be sorted by song name
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        assertTrue(testSubscriber.values().get(0) == Data.Mocks.provideSongs().toList().get(1))
    }

    @Test fun sortArtistName() {
        // given
        assertNotNull(task)
        // when sort use-case invoked with SortType.BY_ARTIST
        val result: Flowable<Song> = task.execute(songsStream, SortType.BY_ARTIST)
        val testSubscriber = TestSubscriber<Song>()
        result.subscribe(testSubscriber)
        // then songs should be sorted by song name
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        assertTrue(testSubscriber.values().get(0) == Data.Mocks.provideSongs().toList().get(2))
    }

    @Test fun sortDate() {
        // given
        assertNotNull(task)
        // when sort use-case invoked with SortType.BY_DATE
        val result: Flowable<Song> = task.execute(songsStream, SortType.BY_DATE)
        val testSubscriber = TestSubscriber<Song>()
        result.subscribe(testSubscriber)
        // then songs should be sorted by song name
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        assertTrue(testSubscriber.values().get(0) == Data.Mocks.provideSongs().toList().get(2))
    }

}