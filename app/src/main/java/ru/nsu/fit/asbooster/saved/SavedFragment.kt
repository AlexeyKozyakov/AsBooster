package ru.nsu.fit.asbooster.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.AudiosActivity
import ru.nsu.fit.asbooster.search.adapter.AudioItem
import ru.nsu.fit.asbooster.search.adapter.AudiosAdapter


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
        initSavedAudiosRecycler()
        presenter.onCreate()
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onShow()
    }

    override fun showAudios(audios: List<AudioItem>) {
        viewHolder.savedAudiosRecycler.adapter = AudiosAdapter(audios) {
            presenter.onAudioClick(it)
        }
    }

    override fun showProgress() {
        viewHolder.savedAudiosRecycler.adapter = null
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

    private fun initSavedAudiosRecycler() {
        viewHolder.savedAudiosRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private class ViewHolder(root: View) {
        val savedAudiosRecycler: RecyclerView = root.findViewById(R.id.saved_recycler_view)
        val progressBar: ProgressBar = root.findViewById(R.id.saved_progress_bar)
        val placeholderTextView: TextView = root.findViewById(R.id.favorites_placeholder_text_view)
        val placeholderImageView: ImageView = root.findViewById(R.id.favorites_placeholder_image_view)
    }

}
