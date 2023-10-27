package jp.co.yumemi.android.code_check.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.DisplayMetrics
import android.util.Log
import android.view.View


/**
 * Utils class for Common methods
 */
class UIUtils {
    companion object {
        /**
         * Convert dp values to px
         */
        private fun convertDpToPixel(dp: Float, context: Context?): Float {
            if (context != null)
                return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
            return 0.0f
        }

        /**
         * Change Element with according to the screen size
         * @param context Context of the View
         * @param view layout element
         * @param widthRatio View width according to widthRatio*(display/width)
         * @param width Screen divide in to (display/width)
         * Ex: width=4 and widthRatio=3 means Actual element width= (Screen Width/4)*3
         * @param margin Horizontal Margin
         */
        fun changeUiSize(context: Context?, view: View, widthRatio: Int, width: Int, margin: Int) {
            if (context != null) {
                val display = context.resources.displayMetrics
                val params = view.layoutParams
                params.width = (display.widthPixels * widthRatio / width - convertDpToPixel(
                    margin.toFloat(),
                    context
                ) * 2).toInt()
                view.layoutParams = params
            }
        }

        /**
         * Change Element with according to the screen size
         * Element has no margin
         * @param context Context of the View
         * @param view layout element
         * @param widthRatio View width according to widthRatio*(display/width)
         * @param width Screen divide in to (display/width)
         * Ex: width=4 and widthRatio=3 means Actual element width= (Screen Width/4)*3
         */
        fun changeUiSize(context: Context?, view: View, widthRatio: Int, width: Int) {
            if (context != null) {
                val display = context.resources.displayMetrics
                val params = view.layoutParams
                params.width = display.widthPixels * widthRatio / width
                view.layoutParams = params
            }
        }


    }

}