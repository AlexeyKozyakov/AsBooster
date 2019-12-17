package ru.nsu.fit.asbooster.player.effects.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.repository.RequestedImage

class EffectsAdapter(
    private val effects: List<EffectItem>,
    private val onClickListener: (adapterPosition: Int) -> Unit
) : RecyclerView.Adapter<EffectsAdapter.ViewHolder>() {

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val effectNameView: TextView = root.findViewById(R.id.effect_name_text_view)
        val effectImageView: ImageView = root.findViewById(R.id.effect_image_view)
        val effectForceSeekBar: SeekBar = root.findViewById(R.id.effect_force_seek_bar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fx_item, parent, false)
        val viewHolder = ViewHolder(view)
        view.setOnClickListener {
            onClickListener(viewHolder.adapterPosition)
        }
        return viewHolder
    }

    override fun getItemCount() = effects.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = with(effects[position]) {
        holder.effectNameView.text = name
        image.show(holder.effectImageView)
        holder.effectForceSeekBar.progress = force
    }

}

class EffectItem(
    val name: String,
    val image: RequestedImage,
    /**
     * Effect force from 0 to 100.
     */
    val force: Int = 0
)