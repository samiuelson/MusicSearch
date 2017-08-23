package me.urbanowicz.samuel.tooplooxmusic.screen.search

import io.reactivex.Flowable
import me.urbanowicz.samuel.tooplooxmusic.data.Data
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalRepository
import me.urbanowicz.samuel.tooplooxmusic.task.SearchLocalSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SearchRemoteSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SortSongsTask
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.verification.VerificationMode
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class SearchPresenterTest {
    val view = Mockito.mock(Contract.View::class.java)
    val localRepository = Mockito.mock(LocalRepository::class.java)
    val searchRemoteSongsTask = Mockito.mock(SearchRemoteSongsTask::class.java)
    val presenter: SearchPresenter =
            Mockito.spy(SearchPresenter(
                    lazy { SearchLocalSongsTask(localRepository) },
                    lazy { searchRemoteSongsTask },
                    lazy { SortSongsTask() }))
    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(localRepository.get()).thenReturn(Data.Mocks.provideSongs())
        Mockito.`when`(searchRemoteSongsTask.execute(Mockito.anyString()))
                .thenReturn(Flowable.fromIterable(Data.Mocks.provideSongs()))
        presenter.onViewAttached(view)
    }

    @Test
    fun onSearchParamsModified() {
        // given
        assertNotNull(presenter)
        assertNotNull(presenter.view)
        // when
        presenter.onSearchParamsModified("happy", true, false, SortType.BY_ARTIST)
        // then
        Mockito.verify(presenter.view)!!.setProgressbarVisibility(true)
        Mockito.verify(presenter).displaySongs(Flowable.fromIterable(Data.Mocks.provideSongs()))
    }

}