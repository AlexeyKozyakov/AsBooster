package ru.nsu.fit.asbooster.player

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.nsu.fit.asbooster.base.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.base.BaseActivity
import ru.nsu.fit.asbooster.player.effects.ui.EffectItem
import ru.nsu.fit.asbooster.player.effects.ui.EffectsAdapter
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.base.navigation.Router

class PlayerActivity : BaseActivity(), PlayerView {

    private lateinit var presenter: PlayerPresenter
    private lateinit var viewHolder: ViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val component = (application as App).component.value
            .playerActivityComponentBuilder()
            .activity(this)
            .build()
        viewHolder = ViewHolder()
        presenter = component.getPresenter()
        val trackInfo: Track? = intent.getParcelableExtra(Router.TRACK_INFO_EXTRA)
        presenter.onCreate(trackInfo)
        initPlayPauseClickListener()
        initOnSeekBarChangeListener()
        initEffectsRecycler()
        initSaveButtonListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun setTrack(track: TrackViewItem) = with(track) {
        viewHolder.nameTextView.text = name
        viewHolder.artistTextView.text = author
        cover.show(viewHolder.coverImageView)
        viewHolder.remainingTimeTextView.text = duration
        viewHolder.seekBarPlayer.max = durationInSeconds
    }

    override fun setElapsedTime(time: String) {
        viewHolder.elapsedTimeTextView.text = time
    }

    override fun showPlayButton() {
        viewHolder.playPauseButton.setImageDrawable(getDrawable(R.drawable.icon_button_play))
    }

    override fun showPauseButton() {
        viewHolder.playPauseButton.setImageDrawable(getDrawable(R.drawable.icon_button_pause))
    }

    override fun showEffects(effects: List<EffectItem>) {
        viewHolder.effectsRecycler.adapter = EffectsAdapter(effects) { position, force ->
            presenter.onEffectForceChanged(position, force)
        }
    }

    override fun updateProgressSeekBar(progress: Int) {
        viewHolder.seekBarPlayer.progress = progress
    }

    override fun showProgress() {
        viewHolder.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        viewHolder.progressBar.visibility = View.INVISIBLE
    }

    private fun initPlayPauseClickListener() {
        viewHolder.playPauseButton.setOnClickListener {
            presenter.onPlayPauseClick()
        }
    }

    private fun initEffectsRecycler() {
        viewHolder.effectsRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PlayerActivity)
        }
    }

    private fun initOnSeekBarChangeListener(){
        viewHolder.seekBarPlayer.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onStartTrackingTouch(p0: SeekBar?) = Unit

            override fun onStopTrackingTouch(p0: SeekBar?) = Unit

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if(fromUser)
                    presenter.onSeek(progress)
            }
        })
    }

    private fun initSaveButtonListener() {
        viewHolder.saveButton.setOnClickListener {
            presenter.onSave()
        }
    }

    private inner class ViewHolder {
        val coverImageView: ImageView = findViewById(R.id.image_view_track_cover)
        val nameTextView: TextView = findViewById(R.id.player_song_name)
        val artistTextView: TextView = findViewById(R.id.player_artist)
        val remainingTimeTextView: TextView = findViewById(R.id.remaining_time)
        val elapsedTimeTextView: TextView = findViewById(R.id.elapsed_time)
        val playPauseButton: ImageButton = findViewById(R.id.button_play)
        val effectsRecycler: RecyclerView = findViewById(R.id.effects_recycler_view)
        val seekBarPlayer: SeekBar = findViewById(R.id.seek_bar_player)
        val saveButton: ImageButton = findViewById(R.id.save_track_button)
        val progressBar: ProgressBar = findViewById(R.id.player_progress_bar)
    }

}
