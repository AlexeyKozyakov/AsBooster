package ru.nsu.fit.asbooster.audios.repository

import android.widget.ImageView
import com.squareup.picasso.Picasso
import ru.nsu.fit.asbooster.di.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class ImageProvider @Inject constructor(private val picasso: Picasso) {

    fun provideImage(url: String?) = object : RequestedImage {
        override fun show(view: ImageView) {
            url?.let {
                picasso.load(it).into(view)
            }
        }
    }
}

interface RequestedImage {
    fun show(view: ImageView)
}
