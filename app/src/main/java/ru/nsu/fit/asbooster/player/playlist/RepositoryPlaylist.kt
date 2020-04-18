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
    startPos: Int
): PlayList {

    var currentPos = startPos
    private set

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

    override suspend fun previous(): Track? {
        waitUpdate()
        --currentPos
        return linearPlayList.previous()
    }

    override suspend fun current(): Track {
        waitUpdate()
        return linearPlayList.current()
    }

    override suspend fun next(): Track? {
        waitUpdate()
        ++currentPos
        return linearPlayList.next()
    }

    override fun destroy() {
        repository.removeListener(repositoryListener)
    }

    private fun update() {
        updating = true
        uiScope.launch {
            linearPlayList = LinearPlaylist(repository.getTracks(), currentPos)
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
