package jp.co.yumemi.android.code_check.ui.activities

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UITestActivityTest{
    @Rule
    @JvmField
    val activityScenarioRule = ActivityScenarioRule(UITestActivity::class.java)

    @Before
    fun setup() {
    }
    @Test
    fun testEditText() {
        // Find the EditText by its resource ID (R.id.your_edit_text_id)
        onView(withId(R.id.editTextText))
            .perform(clearText(), typeText("New Text"), closeSoftKeyboard())

        // You can add assertions here to verify the text in the EditText
        onView(withId(R.id.editTextText)).check(matches(withText("New Text")))
    }
    @After
    fun tearDown() {
        //clean up code
    }
}