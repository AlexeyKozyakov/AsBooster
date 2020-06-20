package ru.nsu.fit.asbooster.audios.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.claucookie.miniequalizerlibrary.EqualizerView
import kotlinx.android.synthetic.main.audio_item.view.*
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.repository.RequestedImage

class AudiosAdapter(
    private val audios: MutableList<AudioItem>,
    private val onClickListener: (adapterPosition: Int) -> Unit
) : RecyclerView.Adapter<AudiosAdapter.ViewHolder>() {

    private lateinit var recyclerView: TracksRecyclerView

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val authorView: TextView = root.findViewById(R.id.audio_item_author)
        val nameView: TextView = root.findViewById(R.id.audio_item_name)
        val imageView: ImageView = root.findViewById(R.id.audio_item_image)
        val durationView: TextView = root.findViewById(R.id.track_duration)
        val playsView: TextView = root.findViewById(R.id.audio_plays_count)
        val postDateView: TextView = root.findViewById(R.id.post_date)
        val equalizerView: EqualizerView = root.equalizerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.audio_item, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            onClickListener(viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun getItemCount() = audios.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(audios[position]) {
            holder.authorView.text = author
            holder.nameView.text = name
            image.show(holder.imageView)
            holder.durationView.text = duration
            holder.playsView.text = plays
            holder.postDateView.text = postDate
        }

        showPlayingState(holder, position)
    }

    private fun showPlayingState(holder: ViewHolder, position: Int) {
        with(holder) {
            when(audios[position].state) {
                AudioItemState.PLAYING -> {
                    equalizerView.visibility = View.VISIBLE
                    equalizerView.animateBars()
                }

                AudioItemState.PAUSED -> {
                    equalizerView.visibility = View.VISIBLE
                    equalizerView.stopBars()
                }

                AudioItemState.NONE -> {
                    equalizerView.visibility = View.INVISIBLE
                    equalizerView.animateBars()
                }
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView as TracksRecyclerView
    }

    fun remove(position: Int) {
        audios.removeAt(position)
        notifyItemRemoved(position)
    }

    fun move(positionFrom: Int, positionTo: Int) {
        val item = audios[positionFrom]
        audios.removeAt(positionFrom)
        audios.add(positionTo,  item)
        notifyItemMoved(positionFrom, positionTo)
    }

    fun add(audio: AudioItem) {
        audios.add(audio)
        notifyItemInserted(audios.size - 1)
    }

    fun setState(position: Int, state: AudioItemState) {
        audios[position].state = state
        notifyItemChanged(position)
    }
}

enum class AudioItemState {
    PLAYING,
    PAUSED,
    NONE
}

class AudioItem(
    val name: String,
    val author: String,
    val image: RequestedImage,
    val duration: String,
    val plays: String,
    val postDate: String
) {
    var state: AudioItemState = AudioItemState.NONE
}
