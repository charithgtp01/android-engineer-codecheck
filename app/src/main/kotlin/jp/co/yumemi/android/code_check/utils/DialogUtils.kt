package jp.co.yumemi.android.code_check.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.interfaces.CustomAlertDialogListener
import jp.co.yumemi.android.code_check.ui.dialogs.CustomAlertDialogFragment
import jp.co.yumemi.android.code_check.ui.dialogs.CustomConfirmAlertDialogFragment
import jp.co.yumemi.android.code_check.ui.dialogs.CustomProgressDialogFragment

/**
 * Utils class for Dialogs and Alerts
 */
class DialogUtils {
    companion object {
        /**
         * Custom Alert Dialog with icon
         * @param message Message body
         * @param type Type of the Dialog Success,Fail or Warn Alert
         */
        fun showAlertDialog(
            context: Context, type: String, message: String?,
            dialogButtonClickListener: CustomAlertDialogListener
        ) {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            if (fragmentManager != null) {
                val dialogFragment =
                    CustomAlertDialogFragment.newInstance(message, type, dialogButtonClickListener)
                dialogFragment.show(
                    fragmentManager,
                    DialogConstants.ALERT_DIALOG_FRAGMENT_TAG.value
                )
            }
        }

        /**
         * Custom Alert Dialog with icon Inside Activity
         * @param message Message body
         * @param type Type of the Dialog Success,Fail or Warn Alert
         */
        fun showErrorDialog(
            context: Context, message: String?
        ) {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            if (fragmentManager != null) {
                val dialogFragment = CustomAlertDialogFragment.newInstance(message)
                dialogFragment.show(
                    fragmentManager,
                    DialogConstants.ALERT_DIALOG_FRAGMENT_TAG.value
                )
            }
        }

        /**
         * Custom Alert Dialog with icon Inside Fragment
         * @param message Message body
         * @param type Type of the Dialog Success,Fail or Warn Alert
         */
        fun showErrorDialogInFragment(
            fragment: Fragment, message: String?
        ): DialogFragment? {
            var dialogFragment: DialogFragment? = null
            val fragmentManager = fragment.fragmentManager
            if (fragmentManager != null) {
                dialogFragment = CustomAlertDialogFragment.newInstance(message)
                dialogFragment.show(
                    fragmentManager,
                    DialogConstants.ALERT_DIALOG_FRAGMENT_TAG.value
                )
            }

            return dialogFragment
        }

        /**
         * Custom Confirm Alert Dialog with icon Inside Activity
         * @param message Message body
         * @param dialogButtonClickListener Dialog Button Click event listener
         *
         */
        fun showConfirmAlertDialog(
            context: Context,
            message: String?,
            dialogButtonClickListener: ConfirmDialogButtonClickListener
        ) {
            val fragmentManager = (context as? AppCompatActivity)?.supportFragmentManager
            if (fragmentManager != null) {
                val dialogFragment =
                    CustomConfirmAlertDialogFragment.newInstance(message, dialogButtonClickListener)
                dialogFragment.show(
                    fragmentManager,
                    DialogConstants.CONFIRM_DIALOG_FRAGMENT_TAG.value
                )
            }
        }


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
                        DialogConstants.PROGRESS_DIALOG_FRAGMENT_TAG.value
                    )
                }
            }
            return dialogFragment
        }

        /**
         * Progress Dialog in Fragment
         * @param message progress message
         */
        fun showProgressDialogInFragment(activity: Fragment?, message: String?): DialogFragment? {
            var dialogFragment: DialogFragment? = null
            if (activity != null) {
                val fragmentManager = activity.fragmentManager
                if (fragmentManager != null) {
                    dialogFragment =
                        CustomProgressDialogFragment.newInstance(message)
                    dialogFragment.show(
                        fragmentManager,
                        DialogConstants.PROGRESS_DIALOG_FRAGMENT_TAG.value
                    )
                }
            }
            return dialogFragment
        }
    }
}