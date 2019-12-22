package ru.nsu.fit.asbooster.repository

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.Placeholder
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.BlurTransformation
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class WebImageProvider @Inject constructor(
    private val picasso: Picasso,
    context: Context
) {

    private val blurTransform = BlurTransformation(context, 1, 1)

    //TODO: add error placeholder image
    fun provideImage(imageUrl: String?,
                     lowQualityImageUrl: String? = null,
                     @DrawableRes placeholder: Int? = null) = object : RequestedImage {
        override fun show(view: ImageView) {
            lowQualityImageUrl?.let { lowQuality ->
                val loadRequest = picasso.load(lowQuality)
                placeholder?.let {
                    loadRequest.placeholder(it)
                }
                loadRequest.transform(blurTransform).into(view, object : Callback {
                    override fun onSuccess() {
                        load(imageUrl, view)
                    }

                    override fun onError(e: Exception?) = Unit
                })
            } ?: load(imageUrl, view, placeholder)
        }
    }

    private fun load(
        imageUrl: String?,
        view: ImageView,
        @DrawableRes placeholder: Int? = null
    ) = imageUrl?.let {
        val request = picasso.load(it)
        (placeholder?.let { placeholder ->
            request.placeholder(placeholder)
        } ?: request.placeholder(view.drawable)).into(view)
    }

}

interface RequestedImage {
    fun show(view: ImageView)
}
