package ru.nsu.fit.asbooster.base.lifecicle

interface ApplicationLifecycle {
    interface Listener {

        /**
         * Any activity paused
         */
        fun onAnyActivityPause() = Unit

        /**
         * Any activity resumed
         */
        fun onAnyActivityResume() = Unit
    }

    fun addListener(listener: Listener)
}

object ApplicationLifecycleImpl : ApplicationLifecycle {

    private val listeners: MutableList<ApplicationLifecycle.Listener> = mutableListOf()

    override fun addListener(listener: ApplicationLifecycle.Listener) {
        listeners.add(listener)
    }

    fun onActivityResume() {
        notifyResumed()
    }

    fun onActivityPause() {
        notifyPaused()
    }

    private fun notifyResumed() {
        listeners.forEach { it.onAnyActivityResume() }
    }

    private fun notifyPaused() {
        listeners.forEach { it.onAnyActivityPause() }
    }
}