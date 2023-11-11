package jp.co.yumemi.android.code_check.ui.fragments.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {
        // Navigate to HomeFragment before each test
        onView(withId(R.id.homeFragment)).perform(click())
    }

    @Test
    fun testSearchWithValidInput() {
        // Type a valid search query in the search view
        onView(withId(R.id.searchInputText)).perform(typeText("android"))

        // Click on the search button
        onView(withId(R.id.searchInputText)).perform(pressImeActionButton())

        Thread.sleep(5000)

        // Check if the RecyclerView is displayed
        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))

        // Add more assertions based on your UI
    }

    @Test
    fun testSearchWithEmptyInput() {
        // Type an empty search query in the search view
        onView(withId(R.id.searchInputText)).perform(typeText(""))

        // Click on the search button
        onView(withId(R.id.searchInputText)).perform(pressImeActionButton())

        // Check if an error message is displayed
        onView(withText(R.string.search_input_empty_error)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchWithNoInternet() {
        // Simulate no internet connection
        // You may want to use a testing library or framework to simulate network conditions

        // Type a valid search query in the search view
        onView(withId(R.id.searchInputText)).perform(typeText("android"))

        // Click on the search button
        onView(withId(R.id.searchInputText)).perform(pressImeActionButton())

        // Check if an error message for no internet is displayed
        onView(withText(R.string.no_internet)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown() {
        // Clean up after each test if needed
        Dispatchers.resetMain()
    }
}