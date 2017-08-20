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
import me.urbanowicz.samuel.tooplooxmusic.task.GetAllSongsTask
import me.urbanowicz.samuel.tooplooxmusic.task.SearchLocalSongsTask

class SearchActivity : AppCompatActivity(), Contract.View {

    private object constants {
        val KEY_LOCAL_SOURCE = "local"
        val KEY_REMOTE_SOURCE = "remote"
        val KEY_SORT_BY_SONG = "song"
        val KEY_SORT_BY_ARTIST = "artist"
        val KEY_SORT_BY_DATE = "release_date"
    }

    private val presenter: Contract.Presenter
    private val adapter = SongsAdapter()

    private var menuItemLocalSource: MenuItem? = null
    private var menuItemRemoteSource: MenuItem? = null
    private var menuItemSortSong: MenuItem? = null
    private var menuItemSortArtist: MenuItem? = null
    private var menuItemSortDate: MenuItem? = null
    private var savedInstanceState: Bundle? = null

    init {
        val searchLocalTaskLazy: Lazy<SearchLocalSongsTask> = lazy<SearchLocalSongsTask> {
            SearchLocalSongsTask(LocalRepository(assets.getAsString("local_songs.json")))
        }
        val getAllSongsTaskLazy: Lazy<GetAllSongsTask> = lazy<GetAllSongsTask> {
            GetAllSongsTask(LocalRepository(assets.getAsString("local_songs.json")), RemoteRepository())
        }
        presenter = SearchPresenter(searchLocalTaskLazy, getAllSongsTaskLazy)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)
        presenter.onViewAttached(this)
        setSupportActionBar(toolbar)
        search_view.onTextChanged { searchQuery ->
            presenter.onSearchQueryModified(searchQuery)
        }
        songs_recycler.adapter = adapter
        songs_recycler.setHasFixedSize(true)

        this.savedInstanceState = savedInstanceState
    }

    override fun onResume() {
        super.onResume()
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

        this.menuItemLocalSource = menu?.findItem(R.id.action_local_source)
        this.menuItemRemoteSource = menu?.findItem(R.id.action_remote_source)
        this.menuItemSortSong = menu?.findItem(R.id.action_sort_by_name)
        this.menuItemSortArtist = menu?.findItem(R.id.action_sort_by_artist)
        this.menuItemSortDate = menu?.findItem(R.id.action_sort_by_date)

        updateStateOfMenuItems()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_local_source -> {
                item.isChecked = !item.isChecked
                presenter.onLocalSourceToggled(item.isChecked)
                return true
            }
            R.id.action_remote_source -> {
                item.isChecked = !item.isChecked
                presenter.onRemoteSourceToggled(item.isChecked)
                return true
            }
            R.id.action_sort_by_date -> {
                item.isChecked = !item.isChecked
                presenter.onSortByDateSelected()
                return true
            }
            R.id.action_sort_by_name -> {
                item.isChecked = !item.isChecked
                presenter.onSortBySongSelected()
                return true
            }
            R.id.action_sort_by_artist -> {
                item.isChecked = !item.isChecked
                presenter.onSortByArtistSelected()
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
