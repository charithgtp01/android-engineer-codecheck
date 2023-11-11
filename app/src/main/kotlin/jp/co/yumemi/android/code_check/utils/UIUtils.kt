package jp.co.yumemi.android.code_check.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.Menu
import android.view.View
import jp.co.yumemi.android.code_check.LocalHelper
import jp.co.yumemi.android.code_check.R


/**
 * Utils class for common UI-related methods.
 */
class UIUtils {
    /**
     * Companion object containing utility methods for UI-related operations.
     */
    companion object {

        /**
         * Set bottom menu label values according to the language.
         *
         * @param context The context of the application.
         * @param menu The menu to be updated with localized labels.
         */
        fun updateMenuValues(context: Context?, menu: Menu?) {
            context?.let { ctx ->
                menu?.let {
                    //Localize according to the selected app language
                    it.findItem(R.id.homeFragment)?.title =
                        LocalHelper.getString(ctx, R.string.menu_home)
                    it.findItem(R.id.favouritesFragment)?.title =
                        LocalHelper.getString(ctx, R.string.menu_favourites)
                    it.findItem(R.id.settingsFragment)?.title =
                        LocalHelper.getString(ctx, R.string.menu_settings)
                }
            }
        }

        /**
         * Convert dp values to pixels.
         *
         * @param dp The value in dp to be converted to pixels.
         * @param context The context of the application.
         * @return The converted value in pixels.
         */
        private fun convertDpToPixel(dp: Float, context: Context?): Float {
            return context?.run {
                dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            } ?: 0.0f
        }

        /**
         * Change the size of a UI element according to the screen size.
         *
         * @param context The context of the view.
         * @param view The layout element to be resized.
         * @param widthRatio View width according to widthRatio*(display/width).
         * @param width Screen divide into (display/width).
         * @param margin Horizontal margin.
         * Ex: width=4 and widthRatio=3 means the actual element width= ((Screen Width/4)*3)-(2*margin).
         *
         */
        fun changeUiSize(context: Context?, view: View, widthRatio: Int, width: Int, margin: Int) {
            context?.let { ctx ->
                val display = ctx.resources.displayMetrics
                view.layoutParams?.let { params ->
                    params.width =
                        ((display.widthPixels * widthRatio) / width - convertDpToPixel(
                            margin.toFloat(),
                            ctx
                        ) * 2).toInt()
                    view.layoutParams = params
                }
            }
        }

        /**
         * Change the size of a UI element according to the screen size.
         * Element has no margin.
         *
         * @param context The context of the view.
         * @param view The layout element to be resized.
         * @param widthRatio View width according to widthRatio*(display/width).
         * @param width Screen divide into (display/width).
         * Ex: width=4 and widthRatio=3 means the actual element width= (Screen Width/4)*3.
         */
        fun changeUiSize(context: Context?, view: View, widthRatio: Int, width: Int) {
            context?.let { ctx ->
                val display = ctx.resources.displayMetrics
                view.layoutParams?.let { params ->
                    params.width =
                        display.widthPixels * widthRatio / width
                    view.layoutParams = params
                }
            }
        }
    }
}