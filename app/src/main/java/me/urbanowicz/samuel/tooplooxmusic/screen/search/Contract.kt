package me.urbanowicz.samuel.tooplooxmusic.screen.search

import me.urbanowicz.samuel.tooplooxmusic.data.Song

interface Contract {

    interface View {
        fun setProgressbarVisibility(visible: Boolean)
        fun displaySongs(songs: List<Song>)
    }

    interface Presenter {
        fun onViewAttached(v: View)
        fun onViewDetached()
        fun onSearchParamsModified(query: String,
                                   useLocalSource: Boolean,
                                   useRemoteSource: Boolean,
                                   sortingType: SortingType)

    }

}