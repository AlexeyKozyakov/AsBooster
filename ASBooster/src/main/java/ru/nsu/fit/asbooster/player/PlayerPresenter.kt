package ru.nsu.fit.asbooster.player

import android.util.Log
import ru.nsu.fit.asbooster.audios.repository.entity.AudioInfo
import javax.inject.Inject

class PlayerPresenter @Inject constructor() {

    fun onCreate(audioInfo: AudioInfo) {
        Log.d("AS_BOOSTER", "create with: $audioInfo")
    }

}