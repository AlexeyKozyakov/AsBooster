package ru.nsu.fit.asbooster.audios

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import ru.nsu.fit.asbooster.di.FragmentScoped
import javax.inject.Inject

/**
 * Class example to show how dependency injection works.
 * This class injected in presenter.
 */
@FragmentScoped
class HelloTextProvider @Inject constructor(
    private val uiScope: CoroutineScope,
    private val background: CoroutineDispatcher
) {
    fun provideTextAsync() = uiScope.async(background) {
        delay(1000)
        "Hello World!"
    }
}