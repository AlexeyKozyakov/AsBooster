package ru.nsu.fit.asbooster.audios

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.asbooster.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.audios.ui.AudiosAdapter

class AudiosActivity : AppCompatActivity(), AudiosView {

    private lateinit var presenter: AudiosPresenter
    private lateinit var viewHolder: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audios)
        val component = (application as App).component.value
            .audiosActivityComponenBuilder()
            .activity(this)
            .build()
        presenter = component.getPresenter()
        viewHolder = ViewHolder()
        initAudiosRecycler()
        initSearchFieldListener()
    }

    override fun showProgress() {
        clearAudios()
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.GONE
    }

    override fun showAudios(audios: List<AudioInfo>) {
        viewHolder.audiosRecycler.adapter = AudiosAdapter(audios) {
            presenter.onAudioClick(it)
        }
    }

    override fun clearAudios() {
        viewHolder.audiosRecycler.adapter = null
    }

    private fun initAudiosRecycler() {
        viewHolder.audiosRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@AudiosActivity)
        }
    }

    private fun initSearchFieldListener() {
        viewHolder.searchField.addTextChangedListener {
            presenter.onQueryChanged(it.toString())
        }
    }

    private inner class ViewHolder {
        val progressBar: ProgressBar = findViewById(R.id.progress_bar_audios)
        val audiosRecycler: RecyclerView = findViewById(R.id.audios_recycler_view)
        val searchField: AutoCompleteTextView = findViewById(R.id.search_field_audios)
    }
}
