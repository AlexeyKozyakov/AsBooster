package ru.nsu.fit.asbooster.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.fit.asbooster.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.search.navigation.AUDIO_INFO_EXTRA
import ru.nsu.fit.asbooster.player.effects.ui.EffectItem
import ru.nsu.fit.asbooster.player.effects.ui.EffectsAdapter

class PlayerActivity : AppCompatActivity(), PlayerView {

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
        presenter.onCreate(intent.getParcelableExtra(AUDIO_INFO_EXTRA)!!)
        initPlayPauseClickListener()
        initOnSeekBarChangeListener()
        initEffectsRecycler()
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
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if(fromUser)
                    presenter.onSeek(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Do something
            }
        })
    }

    fun initSaveButtonListener() {
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
    }

}
