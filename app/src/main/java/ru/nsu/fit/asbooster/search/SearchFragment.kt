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
import androidx.recyclerview.widget.RecyclerView

import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.AudiosActivity
import ru.nsu.fit.asbooster.search.adapter.AudioItem
import ru.nsu.fit.asbooster.search.adapter.AudiosAdapter


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

    override fun showProgress() {
        clearAudios()
        hideEmptyAudiosImage()
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.GONE
    }

    override fun showAudios(audios: List<AudioItem>) {
        viewHolder.audiosRecycler.adapter =
            AudiosAdapter(audios) {
                presenter.onAudioClick(it)
            }
    }

    override fun clearAudios() {
        viewHolder.audiosRecycler.adapter = null
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

    private fun initAudiosRecycler() {
        viewHolder.audiosRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun initSearchFieldListener() {
        viewHolder.searchField.addTextChangedListener {
            presenter.onQueryChanged(it.toString())
        }
    }

    private class ViewHolder(root: View) {
        val progressBar: ProgressBar = root.findViewById(R.id.progress_bar_audios)
        val audiosRecycler: RecyclerView = root.findViewById(R.id.saved_recycler_view)
        val searchField: AutoCompleteTextView = root.findViewById(R.id.search_field_audios)
        val emptyAudiosImageView: ImageView = root.findViewById(R.id.empty_audios_image_view)
        val emptyAudiosTextView: TextView = root.findViewById(R.id.empty_audios_text_view)
    }

}
