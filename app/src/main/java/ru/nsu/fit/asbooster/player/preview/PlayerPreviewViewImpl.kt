package ru.nsu.fit.asbooster.player.preview

import android.app.Activity
import android.view.View
import android.view.ViewStub
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.search.adapter.AudioItem
import javax.inject.Inject

@ActivityScoped
class PlayerPreviewViewImpl @Inject constructor(
    private val activity: Activity,
    private val presenter: PlayerPreviewPresenter
) : PlayerPreviewView {

    private val viewHolder = lazy {
        val inflatedView = activity
            .findViewById<ViewStub>(R.id.player_preview_stub)
            .inflate()
        val viewHolder = ViewHolder(inflatedView)
        initListeners(viewHolder)
        viewHolder
    }

    override fun show(audioItem: AudioItem) {
        with(viewHolder.value) {
            root.visibility = View.VISIBLE
            audioAuthorView.text = audioItem.author
            audioNameView.text = audioItem.name
            audioItem.image.show(audioImage)
            allTime.text = audioItem.duration
        }
    }

    override fun hide() {
        viewHolder.value.root.visibility = View.GONE
    }

    override fun showPlayButton() {
        viewHolder.value.playButton.setImageResource(R.drawable.icon_button_play_preview)
    }

    override fun showPauseButton() {
        viewHolder.value.playButton.setImageResource(R.drawable.icon_button_pause_prevew)
    }

    override fun showProgress() {
        with(viewHolder.value) {
            playButton.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgress() {
        with(viewHolder.value) {
            playButton.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }
    }

    override fun setElapsed(duration: String) {
        viewHolder.value.elapsedTime.text = duration
    }

    private fun initListeners(viewHolder: ViewHolder) {
        viewHolder.playButton.setOnClickListener {
            presenter.onPlayClick()
        }

        viewHolder.closeButton.setOnClickListener {
            presenter.onCloseClick()
        }

        viewHolder.root.setOnClickListener {
            presenter.onOpenClick()
        }

        viewHolder.favoritesButton.setOnClickListener {
            presenter.onAddToFavorites()
        }
    }

    private inner class ViewHolder(val root: View) {
        val playButton: ImageButton = root.findViewById(R.id.button_preview_play)
        val closeButton: ImageButton = root.findViewById(R.id.button_preview_close)
        val progressBar: ProgressBar = root.findViewById(R.id.player_preview_progress_bar)
        val audioImage: ImageView = root.findViewById(R.id.player_preview_track_image)
        val audioAuthorView: TextView = root.findViewById(R.id.player_preview_author)
        val audioNameView: TextView = root.findViewById(R.id.player_preview_name)
        val elapsedTime: TextView = root.findViewById(R.id.player_preview_elapsed_time)
        val allTime: TextView = root.findViewById(R.id.player_preview_all_time)
        val favoritesButton: ImageButton = root.findViewById(R.id.preview_favorites_button)
    }

}
