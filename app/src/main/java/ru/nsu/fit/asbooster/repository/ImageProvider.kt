package ru.nsu.fit.asbooster.repository

import android.content.Context
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ImageProvider @Inject constructor(
    private val picasso: Picasso,
    context: Context
) {

    private val blurTransform = BlurTransformation(context, 1, 1)

    //TODO: add error placeholder image
    fun provideImage(imageUrl: String?, lowQualityImageUrl: String? = null) = object : RequestedImage {
        override fun show(view: ImageView) {
            lowQualityImageUrl?.let { lowQuality ->
                picasso.load(lowQuality).transform(blurTransform).into(view, object : Callback {
                    override fun onSuccess() {
                        load(imageUrl, view)
                    }

                    override fun onError(e: Exception?) = Unit
                })
            } ?: load(imageUrl, view)
        }
    }

    private fun load(imageUrl: String?, view: ImageView) = imageUrl?.let {
        picasso.load(it).placeholder(view.drawable).into(view)
    }

}

interface RequestedImage {
    fun show(view: ImageView)
}
