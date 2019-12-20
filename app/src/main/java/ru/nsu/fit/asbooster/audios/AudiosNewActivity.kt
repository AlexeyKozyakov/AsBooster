package ru.nsu.fit.asbooster.audios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.audios.ui.AudiosPagerAdapter

class AudiosNewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audios_new)
        findViewById<ViewPager>(R.id.audios_view_pager).adapter =
            AudiosPagerAdapter(this, supportFragmentManager)
    }
}
