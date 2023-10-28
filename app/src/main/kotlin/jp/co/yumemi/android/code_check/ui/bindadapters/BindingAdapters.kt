package jp.co.yumemi.android.code_check.ui.bindadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import jp.co.yumemi.android.code_check.R

/**
 * Bind Adapter to set custom attributes
 */
object BindingAdapters {
    @BindingAdapter("imageUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            imageView.load(url) {
                crossfade(true)
                placeholder(R.mipmap.user_place_holder) // Optional placeholder image
            }
        }
    }
}