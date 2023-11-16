package jp.co.yumemi.android.code_check.ui.fragments.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import jp.co.yumemi.android.code_check.models.MockObjects.Companion.errorServerResponse
import jp.co.yumemi.android.code_check.models.MockObjects.Companion.responseErrorMessage
import jp.co.yumemi.android.code_check.models.MockObjects.Companion.successServerResponse
import jp.co.yumemi.android.code_check.utils.getOrAwaitValue
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for the [HomeViewModel] class, which is responsible for testing the functionality
 * of the Home screen's ViewModel.
 *
 * This class uses JUnit4 and Mockito for testing, along with [InstantTaskExecutorRule] and
 * [TestCoroutineDispatcher] to handle asynchronous tasks.
 *
 * @see HomeViewModel
 * @see GitHubRepository
 * @see LocalGitHubRepository
 *
 * @constructor Creates a new instance of [HomeViewModelTest].
 */
@RunWith(JUnit4::class)
class HomeViewModelTest {

    // Create a test coroutine dispatcher to handle asynchronous tasks
    private val testDispatcher = TestCoroutineDispatcher()

    // Rule to execute tasks immediately in the testing environment
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    // Mock GitHub repository for testing
    @Mock
    private lateinit var gitHubRepository: GitHubRepository

    // Mock local GitHub repository for testing
    @Mock
    private lateinit var localGitHubRepository: LocalGitHubRepository

    // ViewModel under test
    private lateinit var viewModel: HomeViewModel

    /**
     * Setup method executed before each test.
     * Initializes the necessary mocks and sets up the [HomeViewModel].
     */
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        // Initialize ViewModel with mocked repositories
        viewModel = HomeViewModel(gitHubRepository, localGitHubRepository)
    }

    /**
     * Test case for successful GitHub repository list retrieval.
     * Verifies that LiveData values are set correctly after calling [HomeViewModel.getGitHubRepoList].
     */
    @Test
    fun `test successful GitHub repository list retrieval`() {
        // Given
        val inputText = "android"

        runBlocking {
            // Mock API response
            `when`(gitHubRepository.getRepositories(inputText)).thenReturn(successServerResponse)

            // When
            viewModel.getGitHubRepoList(inputText)

            // Then
            // Verify that the LiveData values have been set correctly
            assertEquals(successServerResponse.items, viewModel.gitHubRepoList.value)
            assertNull(viewModel.errorMessage.value)
            assertFalse(viewModel.isDialogVisible.value!!)
        }
    }

    /**
     * Test case for failed GitHub repository list retrieval.
     * Verifies that LiveData values are set correctly after calling [HomeViewModel.getGitHubRepoList]
     * with an invalid input.
     */
    @Test
    fun `test failed GitHub repository list retrieval`() {
        val inputText = "invalid_input"

        runBlocking {
            // Mock API response
            `when`(gitHubRepository.getRepositories(inputText)).thenReturn(errorServerResponse)

            // When
            viewModel.getGitHubRepoList(inputText)

            // Then
            // Verify that the LiveData values have been set correctly
            assertNull(viewModel.gitHubRepoList.value)
            assertEquals(responseErrorMessage, viewModel.errorMessage.value)
            assertFalse(viewModel.isDialogVisible.value!!)
        }
    }

    /**
     * Test case for setting value to the errorMessage live data using [HomeViewModel.setErrorMessage].
     * Verifies that the value is correctly set and can be retrieved.
     */
    @Test
    fun `test set value to the errorMessage live data using setErrorMessage`() {
        viewModel.setErrorMessage("No Internet")
        val result = viewModel.errorMessage.getOrAwaitValue()
        assertEquals("No Internet", result)
    }

    /**
     * Test case for setting value to the searchViewHint live data using [HomeViewModel.setSearchViewHint].
     * Verifies that the value is correctly set and can be retrieved.
     */
    @Test
    fun `test set value to the searchViewHint live data using setSearchViewHint`() {
        viewModel.setSearchViewHint("charith")
        val result = viewModel.searchViewHint.getOrAwaitValue()
        assertEquals("charith", result)
    }

    /**
     * Tear-down method executed after each test.
     * Resets the main dispatcher.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
