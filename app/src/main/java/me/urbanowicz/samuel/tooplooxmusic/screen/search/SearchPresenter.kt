package me.urbanowicz.samuel.tooplooxmusic.screen.search

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.urbanowicz.samuel.tooplooxmusic.task.GetAllSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SearchLocalSongsTask

class SearchPresenter(val searchLocalSongsTask: Lazy<SearchLocalSongsTask>,
                      val getAllSongsTask: Lazy<GetAllSongsTask>) : Contract.Presenter {
    private val TAG = "SearchPresenter"

    private var view: Contract.View? = null

    override fun onViewAttached(v: Contract.View) {
        view = v
        displayAllSongs()
    }

    override fun onViewDetached() {
        view = null
    }

    override fun onSortBySongSelected() {
        TODO("not implemented")
    }

    override fun onSortByArtistSelected() {
        TODO("not implemented")
    }

    override fun onSortByDateSelected() {
        TODO("not implemented")
    }

    override fun onSortDateSelected() {
        TODO("not implemented")
    }

    override fun onSearchQueryModified(query: String) {
        if (query.isBlank()) {
            displayAllSongs()
        } else {

        }
    }

    override fun onLocalSourceToggled(state: Boolean) {
        TODO("not implemented")
    }

    override fun onRemoteSourceToggled(state: Boolean) {
        TODO("not implemented")
    }

    private fun displayAllSongs() {
        view?.setProgressbarVisibility(true)
        getAllSongsTask.value.execute()
                .subscribeOn(Schedulers.io())
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { songs ->
                            view?.setProgressbarVisibility(false)
                            view?.displaySongs(songs)
                        },
                        { error ->
                            view?.setProgressbarVisibility(false)
                            Log.e(TAG, error.message)
                        }
                )
    }
}