package ru.nsu.fit.asbooster.base

import android.app.Application
import android.content.Context
import java.io.BufferedReader
import java.io.BufferedWriter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FileIoHelper @Inject constructor(
    private val application: Application
) {

    fun useFileWriter(
        filename: String,
        block: (BufferedWriter) -> Unit,
        mode: Int = Context.MODE_PRIVATE
    ) {
        application.openFileOutput(filename, mode).bufferedWriter().use(block)
    }

    fun useFileReader(
        filename: String,
        block: (BufferedReader) -> Unit
    ) {
        val file = application.getFileStreamPath(filename)
        if (!file.exists()) {
            return
        }
        application.openFileInput(filename).bufferedReader().use(block)
    }

    /**
     * Names of all used files in one place.
     */
    companion object {
        const val TRACK_DATABASE_FILENAME = "track_database"
    }

}