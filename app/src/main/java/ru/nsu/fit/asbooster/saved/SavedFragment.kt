package ru.nsu.fit.asbooster.saved

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

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
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onShow()
    }

    override fun showAudios(audios: List<AudioItem>) {
        viewHolder.savedAudiosRecycler.adapter = AudiosAdapter(audios) {
            //TODO
        }
    }

    override fun showProgress() {
        viewHolder.savedAudiosRecycler.adapter = null
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.GONE
    }

    private fun initSavedAudiosRecycler() {
        viewHolder.savedAudiosRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private class ViewHolder(val root: View) {
        val savedAudiosRecycler: RecyclerView = root.findViewById(R.id.saved_recycler_view)
        val progressBar: ProgressBar = root.findViewById(R.id.saved_progress_bar)
    }

}
