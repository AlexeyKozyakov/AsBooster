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
    private val onForceChanged: (adapterPosition: Int, force: Int) -> Unit
) : RecyclerView.Adapter<EffectsAdapter.ViewHolder>() {

    class ViewHolder(root: View) : RecyclerView.ViewHolder(root) {
        val effectNameView: TextView = root.findViewById(R.id.effect_name_text_view)
        val effectImageView: ImageView = root.findViewById(R.id.image_view_fx)
        val effectForceSeekBar: SeekBar = root.findViewById(R.id.seek_bar_fx)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fx_item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.effectForceSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onForceChanged(viewHolder.adapterPosition, progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit

        })
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
    val id: String,
    val name: String,
    val image: RequestedImage,
    val force: Int
)