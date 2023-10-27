package jp.co.yumemi.android.code_check.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.interfaces.CustomAlertDialogListener
import jp.co.yumemi.android.code_check.ui.dialogs.CustomProgressDialogFragment

/**
 * Utils class for Dialogs and Alerts
 */
class DialogUtils {
    companion object {
        /**
         * Progress Dialog Inside Activity
         * @param message progress message
         */
        fun showProgressDialog(context: Context?, message: String?): DialogFragment? {
            var dialogFragment: DialogFragment? = null
            if (context != null) {
                val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
                if (fragmentManager != null) {
                    dialogFragment =
                        CustomProgressDialogFragment.newInstance(message)
                    dialogFragment.show(
                        fragmentManager,
                        DialogConstants.PROGRESS_DIALOG_FRAGMENT_TAG.VALUE
                    )
                }
            }
            return dialogFragment
        }

        /**
         * Progress Dialog in Fragment
         * @param message progress message
         */
        fun showProgressDialogInFragment(activity: Fragment, message: String?): DialogFragment? {
            var dialogFragment: DialogFragment? = null
            if (activity != null) {
                val fragmentManager = activity.fragmentManager
                if (fragmentManager != null) {
                    dialogFragment =
                        CustomProgressDialogFragment.newInstance(message)
                    dialogFragment.show(
                        fragmentManager,
                        DialogConstants.PROGRESS_DIALOG_FRAGMENT_TAG.VALUE
                    )
                }
            }
            return dialogFragment
        }
    }
}