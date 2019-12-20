package ru.nsu.fit.asbooster.view

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import ru.nsu.fit.asbooster.formating.NumberFormatter
import ru.nsu.fit.asbooster.player.effects.EffectImageProvider
import ru.nsu.fit.asbooster.player.effects.EffectNameProvider
import ru.nsu.fit.asbooster.repository.WebImageProvider
import ru.nsu.fit.asbooster.repository.entity.AudioInfo
import ru.nsu.fit.asbooster.saved.model.Track

class ViewItemsMapperTest {

    private val audioInfo: AudioInfo = mock()
    private val track: Track = mock {
        on { audioInfo } doReturn audioInfo
    }

    private val webImageProvider: WebImageProvider = mock {
        on { provideImage(anyOrNull(), anyOrNull(), anyOrNull()) } doReturn mock()
    }
    private val formatter: NumberFormatter = mock()
    private val effectNameProvider: EffectNameProvider = mock()
    private val effectImageProvider: EffectImageProvider = mock {
        on { provideEffectImage(any()) } doReturn mock()
    }

    private val viewItemsMapper = ViewItemsMapper(
        webImageProvider,
        formatter,
        effectNameProvider,
        effectImageProvider
    )
}