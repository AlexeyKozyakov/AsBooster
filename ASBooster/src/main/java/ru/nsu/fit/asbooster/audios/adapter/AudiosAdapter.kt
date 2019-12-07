package ru.nsu.fit.asbooster.audios.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.repository.RequestedImage

class AudiosAdapter(
    private val audios: List<AudioItem>,
    private val onClickListener: (adapterPosition: Int) -> Unit
) :
    RecyclerView.Adapter<AudiosAdapter.ViewHolder>() {

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val authorView: TextView = root.findViewById(R.id.audio_item_author)
        val nameView: TextView = root.findViewById(R.id.audio_item_name)
        val imageView: ImageView = root.findViewById(R.id.audio_item_image)
        val durationView: TextView = root.findViewById(R.id.track_duration)
        val playsView: TextView = root.findViewById(R.id.audio_plays_count)
        val postDateView: TextView = root.findViewById(R.id.post_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_audio_item, parent, false)
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
    }

}

class AudioItem(
    val name: String,
    val author: String,
    val image: RequestedImage,
    val duration: String,
    val plays: String,
    val postDate: String
)