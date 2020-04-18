package ru.nsu.fit.asbooster.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFERENCES_NAME = "base preferences"

@Singleton
class SharedPreferencesHelper @Inject constructor(
    private val application: Application
) {

    fun writeBoolean(key: String, value: Boolean) {
        editPreferences {
            putBoolean(key, value)
        }
    }

    fun readBoolean(key: String, default: Boolean = false) =
        preferences.getBoolean(key, default)

    private fun editPreferences(block: SharedPreferences.Editor.() -> SharedPreferences.Editor) {
        preferences.edit().block().apply()
    }

    private val preferences
        get() = application.getSharedPreferences(
            PREFERENCES_NAME,
            Context.MODE_PRIVATE
        )

    /**
     * All keys in one place.
     */
    companion object {
        const val TRACK_DB_EMPTY_KEY = "track db is empty"
    }

}