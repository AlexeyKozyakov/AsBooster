package ru.nsu.fit.asbooster.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.AudiosActivity
import ru.nsu.fit.asbooster.audios.view.AudioItemState
import ru.nsu.fit.asbooster.audios.view.AudioItem
import ru.nsu.fit.asbooster.audios.view.AudiosAdapter
import ru.nsu.fit.asbooster.audios.view.TracksRecyclerView


class SearchFragment : Fragment(), SearchView {

    private lateinit var viewHolder: ViewHolder
    private lateinit var presenter: SearchPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val component = (activity as AudiosActivity).component.value
            .searchFragmentComponentBuilder()
            .fragment(this)
            .build()
        presenter = component.getPresenter()
        viewHolder = ViewHolder(view)
        initAudiosRecycler()
        initSearchFieldListener()
        presenter.onCreate()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun showProgress() {
        clearAudios()
        hideEmptyAudiosImage()
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.GONE
    }

    override fun showAudios(audios: MutableList<AudioItem>) {
        viewHolder.tracksRecycler.adapter =
            AudiosAdapter(audios) {
                presenter.onAudioClick(it)
            }
    }

    override fun clearAudios() {
        viewHolder.tracksRecycler.adapter = null
    }

    override fun showEmptyAudiosImage() {
        hideProgress()
        clearAudios()
        viewHolder.emptyAudiosImageView.visibility = View.VISIBLE
        viewHolder.emptyAudiosTextView.visibility = View.VISIBLE
    }

    override fun hideEmptyAudiosImage() {
        viewHolder.emptyAudiosImageView.visibility = View.GONE
        viewHolder.emptyAudiosTextView.visibility = View.GONE
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

    private val audiosAdapter get() = viewHolder.tracksRecycler.adapter as AudiosAdapter

    private fun initAudiosRecycler() {
        viewHolder.tracksRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initSearchFieldListener() {
        viewHolder.searchField.addTextChangedListener {
            presenter.onQueryChanged(it.toString())
        }
    }

    private class ViewHolder(val root: View) {
        val progressBar: ProgressBar = root.findViewById(R.id.progress_bar_audios)
        val tracksRecycler: TracksRecyclerView = root.findViewById(R.id.search_recycler_view)
        val searchField: AutoCompleteTextView = root.findViewById(R.id.search_field_audios)
        val emptyAudiosImageView: ImageView = root.findViewById(R.id.empty_audios_image_view)
        val emptyAudiosTextView: TextView = root.findViewById(R.id.empty_audios_text_view)
    }

}
