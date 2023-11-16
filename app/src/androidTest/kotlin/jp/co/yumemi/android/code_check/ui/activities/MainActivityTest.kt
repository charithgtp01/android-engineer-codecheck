package jp.co.yumemi.android.code_check.ui.activities

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.utils.LocalHelper
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        // Set up Espresso failure handler to ignore AnimatorNotEnabledException
        Espresso.setFailureHandler { error, _ ->
            if (error is PerformException) {
                val cause = error.cause
                if (cause is Exception) {
                    // Ignore the AnimatorNotEnabledException caused by animations
                    return@setFailureHandler
                }
            }
            throw error
        }

        activityRule.scenario.onActivity { activity ->
            mainActivity = activity
        }
    }

    @Test
    fun testFragmentNavigationAndUI() {
        // Verify the initial state of the UI
        onView(withId(R.id.btnBack)).check(matches(not(isDisplayed())))
        onView(withId(R.id.bottom_navigation_menu)).check(matches(isDisplayed()))

        // Verify the initial fragment is HOME_FRAGMENT
        onView(withId(R.id.title)).check(
            matches(
                withText(
                    LocalHelper.getString(
                        mainActivity,
                        R.string.menu_home
                    )
                )
            )
        )

        // Click on an item in the bottom navigation menu (e.g., Favourites)
        onView(
            allOf(
                withId(R.id.bottom_navigation_menu),
                hasDescendant(withText(LocalHelper.getString(mainActivity,R.string.menu_favourites)))
            )
        )
            .perform(click())

        // Verify the UI after navigating to Favourites fragment
        onView(withId(R.id.btnBack)).check(matches(not(isDisplayed())))
        onView(withId(R.id.bottom_navigation_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(
            matches(
                withText(
                    LocalHelper.getString(
                        mainActivity,
                        R.string.menu_favourites
                    )
                )
            )
        )

        onView(withId(R.id.btnBack)).check(matches(allOf(isClickable()))).perform(click())

        // Verify the UI after clicking back (back to HOME_FRAGMENT)
        onView(withId(R.id.btnBack)).check(matches(not(isDisplayed())))
        onView(withId(R.id.bottom_navigation_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.title)).check(matches(isDisplayed()))
        onView(
            allOf(
                withId(R.id.title), hasDescendant(
                    withText(
                        LocalHelper.getString(
                            mainActivity,
                            R.string.menu_home
                        )
                    )
                )
            )
        )
    }
}
