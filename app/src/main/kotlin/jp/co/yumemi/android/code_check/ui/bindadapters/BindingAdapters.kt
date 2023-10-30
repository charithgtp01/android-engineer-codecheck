package jp.co.yumemi.android.code_check.ui.bindadapters

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.StringConstants

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

    @BindingAdapter("itemBackground")
    @JvmStatic
    fun setItemBackground(view: View, shouldSelected: Boolean) {
        val icon = view.findViewById<ImageView>(R.id.selectBox)
        if (shouldSelected) {
            icon.setImageResource(R.mipmap.active_radio)
            view.setBackgroundResource(R.drawable.selected_layout_bg)
        } else {
            icon.setImageResource(R.mipmap.radio)
            view.setBackgroundResource(R.drawable.deselected_layout_bg)
        }
    }
}