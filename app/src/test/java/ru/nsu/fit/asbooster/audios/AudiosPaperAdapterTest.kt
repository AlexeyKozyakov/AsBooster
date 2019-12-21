package ru.nsu.fit.asbooster.audios

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.nhaarman.mockitokotlin2.mock
import org.junit.Assert
import org.junit.Test

class AudiosPaperAdapterTest {
    private val context: Context = mock()
    private val fragmentManager: FragmentManager = mock()

    private val audiosPaperAdapter = AudiosPagerAdapter(
        context,
        fragmentManager
    )

    @Test
    fun `get wrong item test`() {
        try {
            audiosPaperAdapter.getItem(-1)
        } catch (e: IllegalStateException) {
            return
        }

        Assert.assertTrue(false)
    }
}