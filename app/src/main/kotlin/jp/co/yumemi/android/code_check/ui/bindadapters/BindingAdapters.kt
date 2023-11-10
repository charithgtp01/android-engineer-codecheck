package jp.co.yumemi.android.code_check.ui.bindadapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import jp.co.yumemi.android.code_check.R

/**
 * This object provides custom Data Binding adapters that can be used to bind data and behavior
 * to views in your Android application layout XML files. These adapters are used to simplify
 * the process of working with custom attributes defined in your XML layouts.
 */
object BindingAdapters {
    /**
     * A Data Binding adapter for loading images from a URL into an ImageView.
     *
     * @param imageView The ImageView to display the loaded image.
     * @param url The URL of the image to be loaded.
     */
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

    /**
     * A Data Binding adapter for setting the background and icon of a view based on a boolean flag.
     *
     * @param view The View for which the background and icon should be set.
     * @param shouldSelected A boolean flag indicating if the view should be selected.
     */
    @BindingAdapter("itemBackground")
    @JvmStatic
    fun setItemBackground(view: View, shouldSelected: Boolean) {
        val icon = view.findViewById<ImageView>(R.id.selectBox)
        when {
            shouldSelected -> {
                icon.setImageResource(R.mipmap.active_radio)
                view.setBackgroundResource(R.drawable.selected_layout_bg)
            }
            else -> {
                icon.setImageResource(R.mipmap.radio)
                view.setBackgroundResource(R.drawable.deselected_layout_bg)
            }
        }
    }

    /**
     * A Data Binding adapter for setting the favorite icon of a view based on a boolean flag.
     *
     * @param view The View for which the favorite icon should be set.
     * @param isFavourite A boolean flag indicating if the item is marked as a favorite.
     */
    @BindingAdapter("favIcon")
    @JvmStatic
    fun setFavIcon(view: View, isFavourite: Boolean) {
        val icon = view.findViewById<ImageView>(R.id.btnFav)
        when {
            isFavourite -> icon.setImageResource(R.mipmap.favourites_icon)
            else -> icon.setImageResource(R.mipmap.no_favourites_icon)
        }
    }
}