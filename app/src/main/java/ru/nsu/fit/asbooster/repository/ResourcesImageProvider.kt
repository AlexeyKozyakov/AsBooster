package ru.nsu.fit.asbooster.repository

import android.widget.ImageView
import androidx.annotation.DrawableRes
import javax.inject.Singleton

@Singleton
class ResourcesImageProvider {

    fun privideImage(@DrawableRes resId: Int) = object : RequestedImage {
        override fun show(view: ImageView) {
            view.setImageResource(resId)
        }
    }

}