package ru.nsu.fit.asbooster.audios

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.nhaarman.mockitokotlin2.mock

class AudiosPaperAdapterTest {
    private val context: Context = mock()
    private val fragmentManager: FragmentManager = mock()

    private val audiosPaperAdapter = AudiosPagerAdapter(
        context,
        fragmentManager
    )
}