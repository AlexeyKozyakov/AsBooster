package ru.nsu.fit.asbooster.base

import androidx.appcompat.app.AppCompatActivity
import ru.nsu.fit.asbooster.base.lifecicle.ApplicationLifecycleImpl

abstract class BaseActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        ApplicationLifecycleImpl.onActivityResume()
    }

    override fun onPause() {
        super.onPause()
        ApplicationLifecycleImpl.onActivityPause()
    }

}
