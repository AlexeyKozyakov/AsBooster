package ru.nsu.fit.asbooster.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.nsu.fit.asbooster.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.navigation.AUDIO_INFO_EXTRA

class PlayerActivity : AppCompatActivity(), PlayerView {

    private lateinit var presenter: PlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val component = (application as App).component.value
            .playerActivityComponentBuilder()
            .activity(this)
            .build()
        presenter = component.getPresenter()
        presenter.onCreate(intent.getParcelableExtra(AUDIO_INFO_EXTRA))
    }

}
