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

    private val presenter: Contract.Presenter
    private val adapter = SongsAdapter()

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
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewDetached()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.action_local_source -> {
                presenter.onLocalSourceToggled(item.isChecked)
                return true
            }
            R.id.action_remote_source -> {
                presenter.onRemoteSourceToggled(item.isChecked)
                return true
            }
            R.id.action_sort_by_date -> {
                presenter.onSortByDateSelected()
                return true
            }
            R.id.action_sort_by_name -> {
                presenter.onSortBySongSelected()
                return true
            }
            R.id.action_sort_by_artist -> {
                presenter.onSortByArtistSelected()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun setProgressbarVisibility(visible: Boolean) {
        progressbar.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun displaySongs(songs: List<Song>) {
        adapter.setSongs(songs)
    }

}
