package me.urbanowicz.samuel.tooplooxmusic.screen.search

class SearchPresenter : Contract.Presenter {

    private var view: Contract.View? = null

    override fun onViewAttached(v: Contract.View) {
        view = v;
    }

    override fun onViewDettached() {
        view = null
    }
}