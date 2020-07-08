package ru.nsu.fit.asbooster.base.listenable


interface Listenable<Listener> {
    fun addListener(listener: Listener)

    fun removeListener(listener: Listener)
}

abstract class ListenableImpl<Listener> : Listenable<Listener> {
    private val listeners = mutableListOf<Listener>()

    override fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    protected fun notify(event: Listener.() -> Unit) = listeners.forEach(event)
}
