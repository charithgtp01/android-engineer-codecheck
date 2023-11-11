package jp.co.yumemi.android.code_check.ui.dialogs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
class CustomConfirmAlertDialogFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun testShowConfirmAlertDialog() {
        // Define a mock ConfirmDialogButtonClickListener
        val mockListener = mock(ConfirmDialogButtonClickListener::class.java)

        // Call the method to show the custom dialog
        activityRule.scenario.onActivity { activity ->
             showConfirmAlertDialog(activity, "Test Message", mockListener)
        }


        // Verify that the dialog is displayed
        onView(withId(R.id.dialogMainLayout)).check(
            matches(isDisplayed())
        )

        onView(withId(R.id.icon)).check(
            matches(isDisplayed())
        )

        onView(withId(R.id.buttonYes)).check(
            matches(isDisplayed())
        )

        onView(withId(R.id.buttonNo)).check(
            matches(isDisplayed())
        )
    }
}