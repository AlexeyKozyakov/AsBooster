package ru.nsu.fit.asbooster.audios

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.fit.asbooster.base.App
import ru.nsu.fit.asbooster.R
import ru.nsu.fit.asbooster.base.lifecicle.ApplicationLifecycleImpl

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
        initDependencies()
    }

    override fun onPause() {
        super.onPause()
        ApplicationLifecycleImpl.onActivityPause()
    }

    override fun onResume() {
        super.onResume()
        ApplicationLifecycleImpl.onActivityResume()
    }

    private fun initDependencies() {
        component.value.getAudiosActivityDependencies()
    }

}
