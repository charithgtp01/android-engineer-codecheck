package jp.co.yumemi.android.code_check.ui.fragments.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.Owner
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import jp.co.yumemi.android.code_check.ui.activities.MainActivity
import jp.co.yumemi.android.code_check.utils.DialogUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private lateinit var viewModel: HomeViewModel
    private lateinit var dialogUtils: DialogUtils

    @Mock
    private lateinit var gitHubRepository: GitHubRepository
    private val mockOwnerObj =
        Owner(avatarUrl = "https://avatars.githubusercontent.com/u/22025488?v=4", type = "User")
    private val mockGitHubRepoObject = GitHubRepoObject(
        id = 1,
        name = "charithvin",
        owner = mockOwnerObj,
        nullableLanguage = "CSS",
        stargazersCount = 10,
        watchersCount = 15,
        forksCount = 4,
        openIssuesCount = 25
    )
    private val mockData = listOf(mockGitHubRepoObject)

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        Dispatchers.setMain(testDispatcher)
        viewModel = HomeViewModel(gitHubRepository)
        dialogUtils = DialogUtils()
        launchHomeFragment()
    }

    @Test
    fun testSearchBar() {
        // Find the search EditText by its ID (replace R.id.searchEditText with your actual ID)
        val searchEditText = onView(withId(R.id.searchInputText))

        // Type a query into the search EditText
        val query = "YourSearchQuery"
        searchEditText.perform(typeText(query))

        // Submit the query using IME_ACTION_SEARCH
        searchEditText.perform(pressImeActionButton())

        // Add assertions as needed to verify the search results or any other behavior
        // For example, you can check if the search results are displayed on the screen.
//        onView(withId(R.id.searchResults)).check(matches(isDisplayed()))
    }

    @Test
    fun searchAndReceiveData() {
        // Launch the activity
//        val scenario = activityScenarioRule.scenario
//
//        // Enter a search query in the EditText
        onView(withId(R.id.searchInputText)).perform(typeText("charit"))
//
//        // Submit the search query using IME_ACTION_SEARCH
        onView(withId(R.id.searchInputText)).perform(pressImeActionButton())

        // For example, if you expect to see a specific item in the RecyclerView with text "Dummy Item":
//        onView(withId(R.id.recyclerView)).check(matches(hasDescendant(withText("Dummy Item"))))
//        onView(withId(R.id.recyclerView)).check(matches(isDisplayed()))
    }

    // A method to launch the HomeFragment
    private fun launchHomeFragment() {
        onView(withId(R.id.homeFragment)).perform(click()) // Replace with the appropriate view ID for your HomeFragment
    }

//    @Test
//    fun testViewModelObservers() {
//        // Mock LiveData objects and data from the ViewModel
//        `when`(viewModel.errorMessage).thenReturn(Mockito.mock(MutableLiveData::class.java) as MutableLiveData<String>)
//        `when`(viewModel.isDialogVisible).thenReturn(Mockito.mock(MutableLiveData::class.java) as MutableLiveData<Boolean>)
//        `when`(viewModel.gitHubRepoList).thenReturn(Mockito.mock(MutableLiveData::class.java) as MutableLiveData<List<GitHubRepoObject>>)
//
//// Set the LiveData values to trigger observers
//        viewModel.errorMessage.postValue("Test error message")
//        viewModel.isDialogVisible.postValue(true)
//        viewModel.gitHubRepoList.postValue(mockData)
//
//        verify(dialogUtils.showErrorDialogInFragment()).showErrorDialogInFragment(eq("Test error message"))
//
//    }

    @After
    fun tearDown() {
        //clean up code
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}