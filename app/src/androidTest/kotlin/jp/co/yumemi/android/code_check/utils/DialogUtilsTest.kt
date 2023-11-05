package jp.co.yumemi.android.code_check.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.ui.dialogs.CustomConfirmAlertDialogFragment
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class DialogUtilsTest {
    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var appCompatActivity: AppCompatActivity

    @Mock
    private lateinit var fragmentManager: FragmentManager

    @Mock
    private lateinit var dialogButtonClickListener: ConfirmDialogButtonClickListener

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testShowConfirmAlertDialog() {
        // Arrange
        `when`(context as? AppCompatActivity).thenReturn(appCompatActivity)
        `when`(appCompatActivity.supportFragmentManager).thenReturn(fragmentManager)
        val message = "Test Message"

        // Act
        showConfirmAlertDialog(context, message, dialogButtonClickListener)

        // Assert
//        verify(fragmentManager).executePendingTransactions() // You might need to call executePendingTransactions() if needed.
//        verify(fragmentManager).beginTransaction()
//        verify(fragmentManager).add(any(), anyString())
//        verify(fragmentManager).commit()

        // Optionally, you can verify that newInstance was called with the correct parameters
        verify(CustomConfirmAlertDialogFragment).newInstance(
            eq(message),
            eq(dialogButtonClickListener)
        )
    }
}
