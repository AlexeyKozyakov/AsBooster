package ru.nsu.fit.asbooster.saved.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import ru.nsu.fit.asbooster.base.FileIoHelper
import ru.nsu.fit.asbooster.base.lifecicle.ApplicationLifecycle
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Singleton
class FileTracksRepository @Inject constructor(
    private val background: CoroutineDispatcher,
    private val gson: Gson,
    private val fileIoHelper: FileIoHelper,
    private val uiScope: CoroutineScope,
    applicationLifecycle: ApplicationLifecycle
) : InMemoryTracksRepository() {

    private val loadCallbacks = mutableListOf<suspend () -> Unit>()

    private var loaded = false

    init {

        uiScope.launch {
            load()
            loaded = true
            loadCallbacks.forEach { it() }
            loadCallbacks.clear()
        }

        applicationLifecycle.addListener(object : ApplicationLifecycle.Listener {
            override fun onAnyActivityPause() {
                uiScope.launch {
                    dump()
                }
            }
        })
    }

    private suspend fun load() {
        withContext(background) {
            fileIoHelper.useFileReader(FileIoHelper.TRACK_DATABASE_FILENAME) { reader ->
                val tracksFromFile =
                    gson.fromJson<List<Track>>(
                        reader,
                        object : TypeToken<List<Track>>() {}.type
                    )
                loadTracks(tracksFromFile)
            }
        }
    }

    private suspend fun dump() {
        withContext(background) {
            val tracksFromMemory = getTracks()
            fileIoHelper.useFileWriter(FileIoHelper.TRACK_DATABASE_FILENAME, { writer ->
                gson.toJson(tracksFromMemory, writer)
            })
        }
    }

    override suspend fun isEmpty() = afterLoad { super.isEmpty() }

    override suspend fun getTracks() = afterLoad { super.getTracks() }

    override suspend fun getTrack(position: Int) = afterLoad { super.getTrack(position) }

    override suspend fun saveTrack(track: Track) = afterLoad { super.saveTrack(track) }

    override suspend fun deleteTrack(position: Int) = afterLoad { super.deleteTrack(position) }

    override suspend fun getPosition(track: Track) = afterLoad { super.getPosition(track) }

    override suspend fun move(track: Track, insertAfter: Track) =
        afterLoad { super.move(track, insertAfter) }

    private suspend fun <T> afterLoad(block: suspend () -> T): T {
        if (loaded) {
            return block()
        }

        return suspendCoroutine { continuation ->
            loadCallbacks.add {
                val res = block()
                continuation.resume(res)
            }
        }
    }

}
