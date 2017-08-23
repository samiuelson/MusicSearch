package me.urbanowicz.samuel.tooplooxmusic.screen.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_search.*
import me.urbanowicz.samuel.tooplooxmusic.R
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import me.urbanowicz.samuel.tooplooxmusic.data.local.LocalRepository
import me.urbanowicz.samuel.tooplooxmusic.data.remote.RemoteRepository
import me.urbanowicz.samuel.tooplooxmusic.extensions.getAsString
import me.urbanowicz.samuel.tooplooxmusic.extensions.onTextChanged
import me.urbanowicz.samuel.tooplooxmusic.task.SearchLocalSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SearchRemoteSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SortSongsTask

class SearchActivity : AppCompatActivity(), Contract.View {

    private object constants {
        val KEY_LOCAL_SOURCE = "local"
        val KEY_REMOTE_SOURCE = "remote"
        val KEY_SORT_BY_SONG = "song"
        val KEY_SORT_BY_ARTIST = "artist"
        val KEY_SORT_BY_DATE = "release_date"
        val KEY_SORT_BY_DEFAULT = "default"
    }

    private val presenter: Contract.Presenter
    private val adapter = SongsAdapter()

    private var menuItemLocalSource: MenuItem? = null
    private var menuItemRemoteSource: MenuItem? = null
    private var menuItemSortSong: MenuItem? = null
    private var menuItemSortArtist: MenuItem? = null
    private var menuItemSortDate: MenuItem? = null
    private var menuItemSortDefault: MenuItem? = null
    private var savedInstanceState: Bundle? = null

    init {
        val searchLocalTaskLazy: Lazy<SearchLocalSongsTask> = lazy<SearchLocalSongsTask> {
            SearchLocalSongsTask(LocalRepository(assets.getAsString("local_songs.json")))
        }
        val searchRemteSongsTaskLazy: Lazy<SearchRemoteSongsTask> = lazy {
            SearchRemoteSongsTask(RemoteRepository())
        }
        val sortSongsTaskLazy: Lazy<SortSongsTask> = lazy<SortSongsTask> {
            SortSongsTask()
        }
        presenter = SearchPresenter(searchLocalTaskLazy, searchRemteSongsTaskLazy, sortSongsTaskLazy)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)
        presenter.onViewAttached(this)
        setSupportActionBar(toolbar)
        search_view.onTextChanged {
            notifyPresenter()
        }
        songs_recycler.adapter = adapter

        this.savedInstanceState = savedInstanceState
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewDetached()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        menuItemLocalSource?.let {
            outState.putBoolean(constants.KEY_LOCAL_SOURCE, menuItemLocalSource!!.isChecked)
        }
        menuItemRemoteSource?.let {
            outState.putBoolean(constants.KEY_REMOTE_SOURCE, menuItemRemoteSource!!.isChecked)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        menuItemLocalSource = menu?.findItem(R.id.action_local_source)
        menuItemRemoteSource = menu?.findItem(R.id.action_remote_source)
        menuItemSortSong = menu?.findItem(R.id.action_sort_by_name)
        menuItemSortArtist = menu?.findItem(R.id.action_sort_by_artist)
        menuItemSortDate = menu?.findItem(R.id.action_sort_by_date)
        menuItemSortDefault = menu?.findItem(R.id.action_sort_default)

        updateStateOfMenuItems()
        notifyPresenter()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_local_source -> {
                item.isChecked = !item.isChecked
                notifyPresenter()
                return true
            }
            R.id.action_remote_source -> {
                item.isChecked = !item.isChecked
                notifyPresenter()
                return true
            }
            R.id.action_sort_by_date -> {
                item.isChecked = !item.isChecked
                notifyPresenter()
                return true
            }
            R.id.action_sort_by_name -> {
                item.isChecked = !item.isChecked
                notifyPresenter()
                return true
            }
            R.id.action_sort_by_artist -> {
                item.isChecked = !item.isChecked
                notifyPresenter()
                return true
            }
            R.id.action_sort_default -> {
                item.isChecked = !item.isChecked
                notifyPresenter()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    private fun updateStateOfMenuItems() {
        savedInstanceState?.let {
            menuItemRemoteSource?.isChecked = savedInstanceState!!.getBoolean(constants.KEY_REMOTE_SOURCE, false)
            menuItemLocalSource?.isChecked = savedInstanceState!!.getBoolean(constants.KEY_LOCAL_SOURCE, false)
            menuItemSortSong?.isChecked = savedInstanceState!!.getBoolean(constants.KEY_SORT_BY_SONG, false)
            menuItemSortArtist?.isChecked = savedInstanceState!!.getBoolean(constants.KEY_SORT_BY_ARTIST, false)
            menuItemSortDate?.isChecked = savedInstanceState!!.getBoolean(constants.KEY_SORT_BY_DATE, false)
            menuItemSortDefault?.isChecked = savedInstanceState!!.getBoolean(constants.KEY_SORT_BY_DEFAULT, false)
        }
    }

    private fun notifyPresenter() {
        val sortType: SortingType
        if (parseBoolean(menuItemSortSong?.isChecked)) {
            sortType = SortingType.BY_SONG
        } else if (parseBoolean(menuItemSortArtist?.isChecked)) {
            sortType = SortingType.BY_ARTIST
        } else if (parseBoolean(menuItemSortDate?.isChecked)) {
            sortType = SortingType.BY_DATE
        } else {
            sortType = SortingType.DEFAULT
        }

        presenter.onSearchParamsModified(
                search_view.text.toString(),
                parseBoolean(menuItemLocalSource?.isChecked),
                parseBoolean(menuItemRemoteSource?.isChecked),
                sortType)
    }

    private fun parseBoolean(value: Boolean?): Boolean {
        if (value != null) {
            return value
        } else {
            return false
        }
    }

    // Contract.View functions
    override fun setProgressbarVisibility(visible: Boolean) {
        progressbar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun displaySongs(songs: List<Song>) {
        adapter.setSongs(songs)
    }

}
