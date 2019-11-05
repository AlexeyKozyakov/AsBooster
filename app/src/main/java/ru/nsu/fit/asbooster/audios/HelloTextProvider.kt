package ru.nsu.fit.asbooster.audios

import javax.inject.Inject

/**
 * Class example to show how dependency injection works.
 * This class injected in presenter.
 */
class HelloTextProvider @Inject constructor() {
    fun provideText() = "Hello World!"
}