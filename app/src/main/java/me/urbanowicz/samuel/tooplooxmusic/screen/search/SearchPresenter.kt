package me.urbanowicz.samuel.tooplooxmusic.screen.search

import android.util.Log
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.task.SearchLocalSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SearchRemoteSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SortSongsTask

class SearchPresenter(val searchLocalSongsTask: Lazy<SearchLocalSongsTask>,
                      val searchRemoteSongsTask: Lazy<SearchRemoteSongsTask>,
                      val sortSongsTask: Lazy<SortSongsTask>) : Contract.Presenter {

    private val TAG = "SearchPresenter"

    private var view: Contract.View? = null

    private var disposables: CompositeDisposable = CompositeDisposable()

    override fun onViewAttached(v: Contract.View) {
        view = v
    }

    override fun onViewDetached() {
        view = null
        disposables.dispose()
    }

    override fun onSearchParamsModified(query: String,
                                        useLocalSource: Boolean,
                                        useRemoteSource: Boolean,
                                        sortType: SortType) {
        view?.displaySongs(emptyList())
        view?.setProgressbarVisibility(true)

        var songs: Flowable<Song> = Flowable.empty()
        if (useLocalSource && useRemoteSource) {
            val localSongs: Flowable<Song> = searchLocalSongsTask.value.execute(query)
            val remoteSongs: Flowable<Song> = searchRemoteSongsTask.value.execute(query)
            songs = Flowable.merge<Song>(localSongs, remoteSongs)
        } else if (useLocalSource) {
            songs = searchLocalSongsTask.value.execute(query)
        } else if (useRemoteSource) {
            songs = searchRemoteSongsTask.value.execute(query)
        } else {
            view?.displaySongs(emptyList())
        }

        displaySongs(sortSongsTask.value.execute(songs, sortType))
    }

    private fun displaySongs(songs: Flowable<Song>) {
        val disposable =
                songs
                    .subscribeOn(Schedulers.io())
                    .toList()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        view?.setProgressbarVisibility(false)
                        view?.displaySongs(it)
                    }, {
                        Log.e(TAG, it.message)
                    })
        disposables.add(disposable)
    }

}