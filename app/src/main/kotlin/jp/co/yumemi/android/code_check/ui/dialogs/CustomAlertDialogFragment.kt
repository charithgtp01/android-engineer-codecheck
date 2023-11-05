package jp.co.yumemi.android.code_check.ui.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.databinding.FragmentCustomAlertDialogBinding
import jp.co.yumemi.android.code_check.interfaces.CustomAlertDialogListener
import jp.co.yumemi.android.code_check.utils.UIUtils.Companion.changeUiSize

/**
 * Custom Alert Dialog Fragment
 */
class CustomAlertDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentCustomAlertDialogBinding

    companion object {
        private const val ARG_MESSAGE = "message"
        private const val ARG_TYPE = "type"
        lateinit var dialogButtonClickListener: CustomAlertDialogListener
        fun newInstance(
            message: String?,
            type: String,
            dialogButtonClickListener: CustomAlertDialogListener
        ): CustomAlertDialogFragment {
            val fragment = CustomAlertDialogFragment()
            this.dialogButtonClickListener = dialogButtonClickListener
            val args = Bundle().apply {
                putString(ARG_MESSAGE, message)
                putString(ARG_TYPE, type)
            }
            fragment.arguments = args
            return fragment
        }

        fun newInstance(
            message: String?,
            type: String
        ): CustomAlertDialogFragment {
            val fragment = CustomAlertDialogFragment()
            val args = Bundle().apply {
                putString(ARG_MESSAGE, message)
                putString(ARG_TYPE, type)
            }
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext(), theme)
        //Remove dialog unwanted bg color in the corners
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //Disable outside click dialog dismiss event
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        //Disable back button pressed dialog dismiss event
        isCancelable = false
        binding = FragmentCustomAlertDialogBinding.inflate(inflater, container, false)
//        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val message = arguments?.getString(ARG_MESSAGE)
        val type = arguments?.getString(ARG_TYPE)
        //Dialog Width with horizontal margin
        changeUiSize(context, binding.dialogMainLayout, 1, 1, 30)
        //Icon width=(Device Width/5)
        changeUiSize(context, binding.icon, 1, 5)
        // Set data to the data binding variables
        binding.dialogMessage = message
        when (type) {
            DialogConstants.SUCCESS.value -> binding.imageResId = R.mipmap.done
            DialogConstants.FAIL.value -> binding.imageResId = R.mipmap.cancel
            DialogConstants.WARN.value -> binding.imageResId = R.mipmap.warning
        }

        binding.button.setOnClickListener {
            //Error Dialog should not want to return button click listener
            try {
                dialogButtonClickListener.onDialogButtonClicked()
            } catch (_: Exception) {

            }
            dismiss()
        }

    }

}