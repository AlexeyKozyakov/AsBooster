package ru.nsu.fit.asbooster.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_player.*
import ru.nsu.fit.asbooster.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.navigation.AUDIO_INFO_EXTRA

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
        presenter.onCreate(intent.getParcelableExtra(AUDIO_INFO_EXTRA))
    }

    override fun setTrack(track: TrackViewItem) = with(track) {
        viewHolder.nameTextView.text = name
        viewHolder.artistTextView.text = author
        cover.show(viewHolder.coverImageView)
        viewHolder.remainingTimeTextView.text = duration
    }

    private inner class ViewHolder {
        val coverImageView: ImageView = findViewById(R.id.image_view_track_cover)
        val nameTextView: TextView = findViewById(R.id.player_song_name)
        val artistTextView: TextView = findViewById(R.id.player_artist)
        val remainingTimeTextView: TextView = findViewById(R.id.remaining_time)
        val elapsedTimeTextView: TextView = findViewById(R.id.elapsed_time)
    }

}
