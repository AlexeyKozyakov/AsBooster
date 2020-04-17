package ru.nsu.fit.asbooster.repository

import android.widget.ImageView
import androidx.annotation.DrawableRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesImageProvider @Inject constructor() {

    fun provideImage(@DrawableRes resId: Int) = object : RequestedImage {
        override fun show(view: ImageView) {
            view.setImageResource(resId)
        }
    }

}