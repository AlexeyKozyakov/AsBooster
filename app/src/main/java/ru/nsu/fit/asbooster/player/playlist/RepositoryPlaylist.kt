package ru.nsu.fit.asbooster.player.playlist

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.nsu.fit.asbooster.saved.model.Track
import ru.nsu.fit.asbooster.saved.model.TracksRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RepositoryPlaylist(
    private val repository: TracksRepository,
    private val uiScope: CoroutineScope,
    private val startPos: Int
): PlayList, EagerPlaylist {

    override val currentPos get() = if (linearPlayList.empty) startPos else linearPlayList.currentPos

    private var linearPlayList: LinearPlaylist = emptyLinearPlaylist()

    private var updating = true

    private val updateCallbacks = mutableListOf<() -> Unit>()

    private val repositoryListener = object : TracksRepository.Listener {
        override fun onUpdate() {
            update()
        }

        override fun onSave() = Unit
    }

    init {
        update()
        repository.addListener(repositoryListener)
    }

    override var looping = true

    override suspend fun hasNext(): Boolean {
        waitUpdate()
        return linearPlayList.hasNext()
    }

    override suspend fun hasPrevious(): Boolean {
        waitUpdate()
        return linearPlayList.hasPrevious()
    }

    override suspend fun peekNext(): Track? {
        waitUpdate()
        return linearPlayList.peekNext()
    }

    override suspend fun previous(): Track? {
        waitUpdate()
        return linearPlayList.previous()
    }

    override suspend fun current(): Track? {
        waitUpdate()
        return linearPlayList.current()
    }

    override suspend fun next(): Track? {
        waitUpdate()
        return linearPlayList.next()
    }

    override fun destroy() {
        repository.removeListener(repositoryListener)
    }

    override suspend fun tracks(): List<Track> {
        waitUpdate()
        return linearPlayList.tracks()
    }

    private fun update() {
        updating = true
        uiScope.launch {
            val newPlayList = LinearPlaylist(repository.getTracks(), currentPos)
            linearPlayList = newPlayList
            updating = false
            updateCallbacks.forEach { it() }
            updateCallbacks.clear()
        }
    }

    private suspend fun waitUpdate() {
        if (!updating) {
            return
        }

        suspendCoroutine<Unit> { updateCallbacks.add { it.resume(Unit) } }
    }
}
