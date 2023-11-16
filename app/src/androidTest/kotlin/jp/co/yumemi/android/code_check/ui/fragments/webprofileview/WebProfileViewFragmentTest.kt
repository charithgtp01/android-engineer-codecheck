package jp.co.yumemi.android.code_check.ui.fragments.webprofileview

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import jp.co.yumemi.android.code_check.ui.fragments.home.RepoListAdapter
import jp.co.yumemi.android.code_check.utils.LocalHelper
import jp.co.yumemi.android.code_check.utils.NetworkUtils
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WebProfileViewFragmentTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        // Navigate to HomeFragment before each test
        Espresso.onView(withId(R.id.homeFragment)).perform(ViewActions.click())
        activityRule.scenario.onActivity { activity ->
            mainActivity = activity
        }
    }

    @Test
    fun testWebPageLoad() {
        // Type a valid search query in the search view
        Espresso.onView(withId(R.id.searchInputText))
            .perform(ViewActions.typeText("Android"))

        if (NetworkUtils.isNetworkAvailable()) {
            // Click on the search button
            Espresso.onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            Thread.sleep(5000)
            Espresso.onView(withId(R.id.recyclerView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                        0,
                        ViewActions.click()
                    )
                )
            // Verify that the RepositoryFragment is launched
            Espresso.onView(withId(R.id.btnMore))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed())).perform(ViewActions.click())
            Espresso.onView(withId(R.id.webView))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        } else {
            // Click on the search button
            Espresso.onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            // Check if the error dialog is displayed
            Espresso.onView(
                ViewMatchers.withText(
                    LocalHelper.getString(
                        mainActivity,
                        R.string.no_internet
                    )
                )
            )
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }
    }

}