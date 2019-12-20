package ru.nsu.fit.asbooster.audios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import ru.nsu.fit.asbooster.App
import ru.nsu.fit.asbooster.R

class AudiosActivity : AppCompatActivity() {

    val component = lazy {
        (application as App).component.value
            .audiosNewActivityComponentBuilder()
            .activity(this)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audios)
        findViewById<ViewPager>(R.id.audios_view_pager).adapter =
            AudiosPagerAdapter(this, supportFragmentManager)
    }
}
