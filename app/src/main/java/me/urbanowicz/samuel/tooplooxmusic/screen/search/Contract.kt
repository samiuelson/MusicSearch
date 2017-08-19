package me.urbanowicz.samuel.tooplooxmusic.screen.search

interface Contract {
    interface View {

    }

    interface Presenter {
        fun onViewAttached(v: View)
        fun onViewDettached()
    }
}