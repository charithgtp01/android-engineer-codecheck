package jp.co.yumemi.android.code_check.ui.fragments.favourites

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import jp.co.yumemi.android.code_check.ui.fragments.home.RepoListAdapter
import jp.co.yumemi.android.code_check.utils.NetworkUtils
import org.hamcrest.CoreMatchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class FavouritesFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var viewModel: FavouritesViewModel

    @Mock
    lateinit var localHubRepository: LocalGitHubRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = FavouritesViewModel(localHubRepository)
        // Navigate to HomeFragment before each test
        onView(withId(R.id.homeFragment)).perform(click())
    }

    @Test
    fun testNavigateToFavouriteWithSavedGitHubList() {
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
                    actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                        0,
                        click()
                    )
                )
            // Verify that the RepositoryFragment is launched
            onView(withId(R.id.ownerIconView))
                .check(matches(isDisplayed()))

            onView(withId(R.id.btnFav)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.yes)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.ok)).perform(click())

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
        } else {
            // Click on the search button
            onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            // Check if the error dialog is displayed
            onView(ViewMatchers.withText(R.string.no_internet))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun testNavigateToFavouriteWithEmptyList() {
        onView(withId(R.id.favouritesFragment)).perform(click())
        onView(withId(R.id.favouritesFragment)).check(
            matches(
                isDisplayed()
            )
        )

        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.emptyImageView)).check(
            matches(
                isDisplayed()
            )
        )
    }

    @Test
    fun testFavouriteItemDelete() {

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
                    actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                        0,
                        click()
                    )
                )
            // Verify that the RepositoryFragment is launched
            onView(withId(R.id.ownerIconView))
                .check(matches(isDisplayed()))

            onView(withId(R.id.btnFav)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.yes)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.ok)).perform(click())

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
            onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed())).perform(
                    actionOnItemAtPosition<FavouriteListAdapter.FavouriteListViewHolder>(
                        0,
                        click()
                    )
                )

            onView(withId(R.id.expandedContent)).check(matches(isDisplayed()))

            // Perform click on the delete button of the first item
            onView(withId(R.id.deleteBtn))
                .perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.yes)).perform(click())
        } else {
            // Click on the search button
            onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            // Check if the error dialog is displayed
            onView(ViewMatchers.withText(R.string.no_internet))
                .check(matches(isDisplayed()))
        }
    }

    @Test
    fun testFavouriteItemExpand() {

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
                    actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                        0,
                        click()
                    )
                )
            // Verify that the RepositoryFragment is launched
            onView(withId(R.id.ownerIconView))
                .check(matches(isDisplayed()))

            onView(withId(R.id.btnFav)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.yes)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.ok)).perform(click())

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
            onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed())).perform(
                    actionOnItemAtPosition<FavouriteListAdapter.FavouriteListViewHolder>(
                        0,
                        click()
                    )
                )

            onView(withId(R.id.expandedContent)).check(matches(isDisplayed()))

        } else {
            // Click on the search button
            onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            // Check if the error dialog is displayed
            onView(ViewMatchers.withText(R.string.no_internet))
                .check(matches(isDisplayed()))
        }
    }

    fun testFavouriteItemCollapse() {

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
                    actionOnItemAtPosition<RepoListAdapter.RepoListViewHolder>(
                        0,
                        click()
                    )
                )
            // Verify that the RepositoryFragment is launched
            onView(withId(R.id.ownerIconView))
                .check(matches(isDisplayed()))

            onView(withId(R.id.btnFav)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.yes)).perform(click())

            onView(withId(R.id.dialogMainLayout))
                .check(matches(isDisplayed()))
            onView(ViewMatchers.withText(R.string.ok)).perform(click())

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
            onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed())).perform(
                    actionOnItemAtPosition<FavouriteListAdapter.FavouriteListViewHolder>(
                        0,
                        click()
                    )
                )

            onView(withId(R.id.expandedContent)).check(matches(isDisplayed()))

            onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed())).perform(
                    actionOnItemAtPosition<FavouriteListAdapter.FavouriteListViewHolder>(
                        0,
                        click()
                    )
                )

            onView(withId(R.id.expandedContent)).check(matches(not(isDisplayed())))

        } else {
            // Click on the search button
            onView(withId(R.id.searchInputText))
                .perform(ViewActions.pressImeActionButton())
            // Check if the error dialog is displayed
            onView(ViewMatchers.withText(R.string.no_internet))
                .check(matches(isDisplayed()))
        }
    }

}