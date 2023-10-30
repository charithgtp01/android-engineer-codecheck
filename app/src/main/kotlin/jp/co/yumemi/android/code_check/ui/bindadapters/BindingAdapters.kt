package jp.co.yumemi.android.code_check.ui.bindadapters

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
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

    @BindingAdapter("itemBackground")
    @JvmStatic
    fun setItemBackground(view: View, res: Int){
//        val mainLayout=view.findViewById<ConstraintLayout>(R.id.mainLayout)
        view.setBackgroundResource(res)
    }
}