package me.urbanowicz.samuel.tooplooxmusic.screen.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_song.view.*
import me.urbanowicz.samuel.tooplooxmusic.R
import me.urbanowicz.samuel.tooplooxmusic.data.Song
import java.text.SimpleDateFormat
import java.util.*

class SongsAdapter: RecyclerView.Adapter<SongsAdapter.ViewHolder>() {

    private var songs: MutableList<Song> = ArrayList<Song>()

    private val format = SimpleDateFormat("dd-MM-yyyy")


    fun setSongs(songs: List<Song>) {
        this.songs.clear()
        this.songs.addAll(songs)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val song = songs.get(position)
        holder.itemView.title.text = song.getSongName()
        holder.itemView.artist.text = song.getArtistName()
        song.getReleaseDate()
        if (song.getReleaseDate() != Date(0)) {
            holder.itemView.release_date.text = format.format(song.getReleaseDate())
        }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

}

