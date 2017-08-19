package me.urbanowicz.samuel.tooplooxmusic.screen.search

import me.urbanowicz.samuel.tooplooxmusic.task.SearchLocalSongsTask

class SearchPresenter(val searchLocalSongsTask: Lazy<SearchLocalSongsTask>) : Contract.Presenter {

    private var view: Contract.View? = null

    override fun onViewAttached(v: Contract.View) {
        view = v
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
        TODO("not implemented")
    }

}