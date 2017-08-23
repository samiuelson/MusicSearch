package me.urbanowicz.samuel.tooplooxmusic.task

import io.reactivex.Flowable
import io.reactivex.subscribers.TestSubscriber
import me.urbanowicz.samuel.tooplooxmusic.data.Data
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalRepository
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchLocalSongsTaskTest {
    val localRepository: LocalRepository = Mockito.mock(LocalRepository::class.java)
    var task: SearchLocalSongsTask = SearchLocalSongsTask(localRepository)

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(localRepository.get()).thenReturn(Data.Mocks.provideSongs())
    }

    @Test fun emptyString() {
        // given non empty local repo
        val songsCount = localRepository.get().size
        // when search use-case invoked
        val result: Flowable<Song> = task.execute("")
        val testSubscriber = TestSubscriber<Song>()
        result.subscribe(testSubscriber)
        // then
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        assertTrue(testSubscriber.values().size == songsCount)
    }

    @Test fun nonEmptyString() {
        // given non empty local repo
        val songsCount = localRepository.get().size
        // when search use-case invoked
        val result: Flowable<Song> = task.execute("marley")
        val testSubscriber = TestSubscriber<Song>()
        result.subscribe(testSubscriber)
        // then
        testSubscriber.assertComplete()
        testSubscriber.assertNoErrors()
        assertTrue(testSubscriber.values().size == 1)
    }
}