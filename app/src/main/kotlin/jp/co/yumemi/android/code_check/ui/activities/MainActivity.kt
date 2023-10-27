/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.activities

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.constants.MessageConstants.EXIT_CONFIRMATION_MESSAGE
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.interfaces.CustomAlertDialogListener
import jp.co.yumemi.android.code_check.utils.DialogUtils
import java.util.*

/**
 * Main Activity Page
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle the back button press here
                DialogUtils.showConfirmAlertDialog(
                    this@MainActivity,
                    EXIT_CONFIRMATION_MESSAGE,
                    object : ConfirmDialogButtonClickListener {
                        override fun onPositiveButtonClick() {
                            finish()
                        }

                        override fun onNegativeButtonClick() {
                        }
                    })
            }
        }

        onBackPressedDispatcher.addCallback(this, callback)
    }

}
