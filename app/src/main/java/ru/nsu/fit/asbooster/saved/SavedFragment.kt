package ru.nsu.fit.asbooster.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_saved.view.*

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.AudiosActivity
import ru.nsu.fit.asbooster.audios.view.AudioItemState
import ru.nsu.fit.asbooster.audios.view.AudioItem
import ru.nsu.fit.asbooster.audios.view.AudiosAdapter
import ru.nsu.fit.asbooster.audios.view.TracksRecyclerView


class SavedFragment : Fragment(), SavedView {

    private lateinit var presenter: SavedPresenter
    private lateinit var viewHolder: ViewHolder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved, container, false)
        val component = (activity as AudiosActivity).component.value
            .savedFragmentComponentBuilder()
            .fragment(this)
            .build()
        presenter = component.getPresenter()
        viewHolder = ViewHolder(view)
        presenter.onCreate()
        initTrackRecyclerListener()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showAudios(audios: MutableList<AudioItem>) {
        viewHolder.tracksRecicler.adapter =
            AudiosAdapter(audios) {
                presenter.onAudioClick(it)
            }
    }

    override fun showProgress() {
        viewHolder.tracksRecicler.adapter = null
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.GONE
    }

    override fun showPlaceholder() {
        viewHolder.placeholderImageView.visibility = View.VISIBLE
        viewHolder.placeholderTextView.visibility = View.VISIBLE
    }

    override fun hidePlaceholder() {
        viewHolder.placeholderImageView.visibility = View.GONE
        viewHolder.placeholderTextView.visibility = View.GONE
    }

    override fun removeAudioItem(position: Int) {
        audiosAdapter.remove(position)
    }

    override fun moveAudioItem(positionFrom: Int, positionTo: Int) {
        audiosAdapter.move(positionFrom, positionTo)
    }

    override fun addAudioItem(audio: AudioItem) {
        audiosAdapter.add(audio)
    }

    override fun showPlaying(position: Int) {
        audiosAdapter.setState(position, AudioItemState.PLAYING)
    }

    override fun showPaused(position: Int) {
        audiosAdapter.setState(position, AudioItemState.PAUSED)
    }

    override fun hideAllInfo(position: Int) {
        audiosAdapter.setState(position, AudioItemState.NONE)
    }

    private val audiosAdapter get() = viewHolder.tracksRecicler.adapter as AudiosAdapter

    private fun initTrackRecyclerListener() {
        viewHolder.tracksRecicler.listener = object : TracksRecyclerView.Listener {
            override fun onMove(positionFrom: Int, positionTo: Int) {
                presenter.onMove(positionFrom, positionTo)
            }

            override fun onSwipe(position: Int) {
                presenter.onSwipe(position)
            }
        }
    }

    private class ViewHolder(root: View) {
        val tracksRecicler: TracksRecyclerView = root.search_recycler_view
        val progressBar: ProgressBar = root.findViewById(R.id.saved_progress_bar)
        val placeholderTextView: TextView = root.findViewById(R.id.favorites_placeholder_text_view)
        val placeholderImageView: ImageView =
            root.findViewById(R.id.favorites_placeholder_image_view)
    }

}