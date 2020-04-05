package ru.nsu.fit.asbooster.player.preview

import dagger.Lazy
import ru.nsu.fit.asbooster.di.ActivityScoped
import ru.nsu.fit.asbooster.player.audio.AudioPlayer
import ru.nsu.fit.asbooster.search.navigation.Router
import javax.inject.Inject

@ActivityScoped
class PlayerPreviewPresenter @Inject constructor(
    private val view: Lazy<PlayerPreviewView>,
    private val player: AudioPlayer,
    private val router: Router
) {

    private val playerListener = object : AudioPlayer.Listener {
        override fun onPLay() {
            with(view.get()) {
                show()
                showPauseButton()
            }
        }

        override fun onPause() {
            view.get().showPlayButton()
        }

        override fun onLoadingStart() {
            view.get().showProgress()
        }

        override fun onLoadingFinish() {
            view.get().hideProgress()
        }

        override fun onComplete() {
            view.get().showPlayButton()
        }
    }

    fun onPlayClick() {
        if (player.playing) {
            player.pause()
            view.get().showPlayButton()
        } else {
            player.play()
            view.get().showPauseButton()
        }
    }

    fun onOpenClick() {
        router.openPlayer()
    }

    fun onCloseClick() {
        view.get().hide()
        player.reset()
    }

    fun onCreate() {
        player.addListener(playerListener)
    }

    fun onDestroy() {
        player.removeListener(playerListener)
    }

}