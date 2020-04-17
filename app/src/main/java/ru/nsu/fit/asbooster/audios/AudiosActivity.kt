package ru.nsu.fit.asbooster.audios

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import ru.nsu.fit.asbooster.base.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.base.BaseActivity

class AudiosActivity : BaseActivity() {

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
        initDependencies()
    }

    private fun initDependencies() {
        component.value.getAudiosActivityDependencies()
    }

}
