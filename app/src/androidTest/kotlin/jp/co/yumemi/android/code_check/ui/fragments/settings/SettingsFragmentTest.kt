package jp.co.yumemi.android.code_check.ui.fragments.settings

import android.view.MenuItem
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class SettingsFragmentTest{
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SettingsViewModel()
        // Navigate to HomeFragment before each test
        onView(withId(R.id.homeFragment)).perform(click())
    }

    @Test
    fun testNavigateToSettingsPage(){
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.settingsFragment)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun testAllElementsAvailable(){
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.settingsFragment)).check(
            matches(
                isDisplayed()
            )
        )

        onView(withId(R.id.textView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.englishLayout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.japaneseLayout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testDefaultLanguageIsEnglish(){
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.settingsFragment)).check(
            matches(
                isDisplayed()
            )
        )

        // You can also check the specific TextView by its ID if available
        // Replace R.id.appLanguageTextView with the actual ID of your TextView
        onView(withId(R.id.textView)).check(matches(withText("App Language")))
    }

    @Test
    fun testEnglishSelected(){
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.settingsFragment)).check(
            matches(
                isDisplayed()
            )
        )

        // Assuming you have a view or button to change the language, click on it
        onView(withId(R.id.englishLayout)).perform(click())

        // Assuming you have a button or item for English language, click on it
        onView(withText("English")).perform(click())
        onView(withId(R.id.textView)).check(matches(withText("App Language")))


        // Assuming the bottom navigation view has the correct ID
        // and the menu items have the correct titles for English language
        onView(withId(R.id.bottom_navigation_menu))
            .check { view, _ ->
                val menu = view as? BottomNavigationView
                checkNotNull(menu) { "The view is not a BottomNavigationView" }

                // Check each menu item individually
                checkMenuTitle(menu.menu.findItem(R.id.homeFragment), "Home")
                checkMenuTitle(menu.menu.findItem(R.id.favouritesFragment), "Favourites")
                checkMenuTitle(menu.menu.findItem(R.id.settingsFragment), "Settings")
            }
    }

    @Test
    fun testJapaneseSelected(){
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.settingsFragment)).check(
            matches(
                isDisplayed()
            )
        )

        // Assuming you have a view or button to change the language, click on it
        onView(withId(R.id.japaneseLayout)).perform(click())

        // Assuming you have a button or item for English language, click on it
        onView(withText("日本語")).perform(click())

        onView(withId(R.id.textView)).check(matches(withText("アプリの言語")))
        // Assuming the bottom navigation view has the correct ID
        // and the menu items have the correct titles for English language
        onView(withId(R.id.bottom_navigation_menu))
            .check { view, _ ->
                val menu = view as? BottomNavigationView
                checkNotNull(menu) { "The view is not a BottomNavigationView" }

                // Check each menu item individually
                checkMenuTitle(menu.menu.findItem(R.id.homeFragment), "家")
                checkMenuTitle(menu.menu.findItem(R.id.favouritesFragment), "お気に入り")
                checkMenuTitle(menu.menu.findItem(R.id.settingsFragment), "設定")
            }
    }

    @Test
    fun testSetRadioButtonsAccordingToSelectedLanguage(){
        onView(withId(R.id.settingsFragment)).perform(click())
        onView(withId(R.id.settingsFragment)).check(
            matches(
                isDisplayed()
            )
        )

        onView(withId(R.id.textView))
            .check(matches(isDisplayed()))
        onView(withId(R.id.englishLayout))
            .check(matches(isDisplayed()))
        onView(withId(R.id.japaneseLayout))
            .check(matches(isDisplayed()))
    }

    private fun checkMenuTitle(menuItem: MenuItem?, expectedTitle: String) {
        checkNotNull(menuItem) { "Menu item not found" }
        assertThat(menuItem.title.toString(), `is`(expectedTitle))
    }
}

