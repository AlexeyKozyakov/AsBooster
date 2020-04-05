package ru.nsu.fit.asbooster.base

import android.app.Application
import ru.nsu.fit.asbooster.di.DaggerApplicationComponent

class App : Application() {

    val component = lazy {
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }
}