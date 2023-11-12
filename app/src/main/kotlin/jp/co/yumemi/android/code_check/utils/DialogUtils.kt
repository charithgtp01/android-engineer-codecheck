package jp.co.yumemi.android.code_check.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.ui.dialogs.CustomAlertDialogFragment
import jp.co.yumemi.android.code_check.ui.dialogs.CustomConfirmAlertDialogFragment
import jp.co.yumemi.android.code_check.ui.dialogs.CustomProgressDialogFragment

/**
 * Utility class for managing custom dialogs in application.
 * This class provides methods to show custom alert dialogs and progress dialogs.
 */
class DialogUtils {
    /**
     * A companion object to provide static methods for creating custom dialogs.
     */
    companion object {
        /**
         * Show a custom alert dialog without any button click event.
         *
         * @param context The context in which the dialog should be shown.
         * @param type The type of the dialog (Success, Fail, or Warn Alert).
         * @param message The message body to be displayed in the dialog.
         */
        fun showAlertDialogWithoutAction(
            context: Context, type: String, message: String?
        ) {
            (context as? AppCompatActivity)?.supportFragmentManager?.let { fragmentManager ->
                CustomAlertDialogFragment.newInstance(message, type).show(
                    fragmentManager,
                    DialogConstants.ALERT_DIALOG_FRAGMENT_TAG.value
                )
            }
        }


        /**
         * Show a custom alert dialog with an icon inside a fragment.
         *
         * @param fragment The fragment in which the dialog should be shown.
         * @param message The message body to be displayed in the dialog.
         * @param type The type of the dialog (Success, Fail, or Warn Alert).
         * @return The created dialog fragment.
         */
        fun showDialogWithoutActionInFragment(
            fragment: Fragment, message: String?, type: String
        ): DialogFragment {

            return fragment.parentFragmentManager.let { fragmentManager ->
                CustomAlertDialogFragment.newInstance(message, type).apply {
                    show(fragmentManager, DialogConstants.ALERT_DIALOG_FRAGMENT_TAG.value)
                }
            }
        }

        /**
         * Show a custom confirm alert dialog with an icon inside an activity.
         *
         * @param context The context in which the dialog should be shown.
         * @param message The message body to be displayed in the dialog.
         * @param dialogButtonClickListener The listener for dialog button click events.
         */
        fun showConfirmAlertDialog(
            context: Context,
            message: String?,
            dialogButtonClickListener: ConfirmDialogButtonClickListener
        ) {

            (context as? AppCompatActivity)?.supportFragmentManager?.let { fragmentManager ->
                CustomConfirmAlertDialogFragment.newInstance(message, dialogButtonClickListener)
                    .show(
                        fragmentManager,
                        DialogConstants.CONFIRM_DIALOG_FRAGMENT_TAG.value
                    )
            }
        }

        /**
         * Show a progress dialog inside a fragment.
         *
         * @param activity The fragment in which the progress dialog should be shown.
         * @param message The progress message to be displayed.
         * @return The created progress dialog fragment.
         */
        fun showProgressDialogInFragment(activity: Fragment?, message: String?): DialogFragment? {
            return activity?.parentFragmentManager?.let { fragmentManager ->
                CustomProgressDialogFragment.newInstance(message).apply {
                    show(fragmentManager, DialogConstants.PROGRESS_DIALOG_FRAGMENT_TAG.value)
                }
            }
        }
    }
}