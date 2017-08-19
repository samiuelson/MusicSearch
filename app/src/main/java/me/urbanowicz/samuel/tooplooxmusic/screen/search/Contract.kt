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

        fun onSortBySongSelected()
        fun onSortByArtistSelected()
        fun onSortByDateSelected()
        fun onSortDateSelected()
        fun onSearchQueryModified(query: String)

        fun onLocalSourceToggled(state: Boolean)
        fun onRemoteSourceToggled(state: Boolean)
    }

}