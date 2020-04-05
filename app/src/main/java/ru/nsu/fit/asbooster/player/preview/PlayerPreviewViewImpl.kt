package ru.nsu.fit.asbooster.player.preview

import android.app.Activity
import android.view.View
import android.view.ViewStub
import android.widget.ImageButton
import android.widget.ProgressBar
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.di.ActivityScoped
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

    override fun show() {
        viewHolder.value.root.visibility = View.VISIBLE
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

    private fun initListeners(viewHolder: ViewHolder) {
        viewHolder.playButton.setOnClickListener {
            presenter.onPlayClick()
        }

        viewHolder.openButton.setOnClickListener {
            presenter.onOpenClick()
        }

        viewHolder.closeButton.setOnClickListener {
            presenter.onCloseClick()
        }
    }

    private class ViewHolder(val root: View) {
        val playButton: ImageButton = root.findViewById(R.id.button_preview_play)
        val openButton: ImageButton = root.findViewById(R.id.button_preview_open)
        val closeButton: ImageButton = root.findViewById(R.id.button_preview_close)
        val progressBar: ProgressBar = root.findViewById(R.id.player_preview_progress_bar)
    }

}
