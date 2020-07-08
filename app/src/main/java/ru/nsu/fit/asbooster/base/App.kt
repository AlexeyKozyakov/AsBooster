package ru.nsu.fit.asbooster.base

import android.app.Application
import ru.nsu.fit.asbooster.di.DaggerApplicationComponent

const val APPLICATION_NAME = "AsBooster"

class App : Application() {

    val component = lazy {
        DaggerApplicationComponent.builder()
            .application(this)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.value.applicationDependencies()
    }
}
