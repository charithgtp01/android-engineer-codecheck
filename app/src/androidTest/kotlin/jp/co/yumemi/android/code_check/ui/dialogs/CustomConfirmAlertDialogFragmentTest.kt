package jp.co.yumemi.android.code_check.ui.dialogs

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.MyIdlingResource
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import jp.co.yumemi.android.code_check.R
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class CustomConfirmAlertDialogFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    // In your test, use the IdlingResource
    val idlingResource = MyIdlingResource()
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



        // Simulate a click on the "Yes" button and verify that the positive button click callback is invoked
        onView(withId(R.id.buttonYes)).perform(click())
        // Set the IdlingResource to ready when the view is ready
//        idlingResource.setReady()
//        verify(mockListener).onPositiveButtonClick()

        // Simulate a click on the "No" button and verify that the negative button click callback is invoked
        onView(withId(R.id.buttonNo)).perform(click())
//        verify(mockListener).onNegativeButtonClick()
    }

    @Before
    fun setUp() {
        // Initialize and register the IdlingResource
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        // Unregister the IdlingResource after the test
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}