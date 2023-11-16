package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import jp.co.yumemi.android.code_check.ui.fragments.home.RepoListAdapter
import jp.co.yumemi.android.code_check.utils.LocalHelper
import jp.co.yumemi.android.code_check.utils.NetworkUtils
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepoDetailsFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var mainActivity: MainActivity

    @Before
    fun setUp() {
        // Navigate to HomeFragment before each test
        onView(withId(R.id.homeFragment)).perform(click())
        activityRule.scenario.onActivity { activity ->
            mainActivity = activity
        }
    }

    @Test
    fun testNavigateToWebProfileViewFragment() {
        // Type a valid search query in the search view
        onView(withId(R.id.searchInputText)).perform(ViewActions.typeText("Android"))

        if (NetworkUtils.isNetworkAvailable()) {
            // Click on the search button
            onView(withId(R.id.searchInputText)).perform(ViewActions.pressImeActionButton())
            Thread.sleep(5000)
            onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed())).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                        0,
                        click()
                    )
                )
            // Verify that the RepositoryFragment is launched
            onView(withId(R.id.btnMore))
                .check(matches(isDisplayed())).perform(click())
            onView(withId(R.id.webView))
                .check(matches(isDisplayed()))
        } else {
            // Click on the search button
            onView(withId(R.id.searchInputText)).perform(ViewActions.pressImeActionButton())
            // Check if the error dialog is displayed
            onView(withText(R.string.no_internet))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun testAddToFavourite() {
        // Type a valid search query in the search view
        onView(withId(R.id.searchInputText))
            .perform(ViewActions.typeText("Android"))

        if (NetworkUtils.isNetworkAvailable()) {
            // Click on the search button
            onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            Thread.sleep(5000)
            onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed())).perform(
                    RecyclerViewActions.actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                        0,
                        click()
                    )
                )

            onView(withId(R.id.btnFav)) .check(matches(isDisplayed())).perform(click())

            onView(
                withText(
                    LocalHelper.getString(
                        mainActivity,
                        R.string.add_fav_confirmation_message
                    )
                )
            ).check(matches(isDisplayed()))

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(withId(R.id.buttonYes)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(withId(R.id.button)).perform(click())

            // Press the back button
            Espresso.pressBack()

            // Check if navigation to HomeFragment is triggered
            onView(withId(R.id.homeFragment)).check(matches(isDisplayed()))

            onView(withId(R.id.favouritesFragment)).perform(click())
            onView(withId(R.id.favouritesFragment)).check(
                matches(
                    isDisplayed()
                )
            )
            onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
            Thread.sleep(5000)
            onView(withId(R.id.emptyImageView)).check(
                matches(
                    not(isDisplayed())
                )
            )
        } else {
            // Click on the search button
            onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            // Check if the error dialog is displayed
            onView(withText(LocalHelper.getString(mainActivity, R.string.no_internet)))
                .check(matches(isDisplayed()))
        }
    }


}