package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.MockObjects.Companion.expectedGitHubRepoObject
import jp.co.yumemi.android.code_check.MockObjects.Companion.mockGitHubRepoObject
import jp.co.yumemi.android.code_check.constants.MessageConstants
import jp.co.yumemi.android.code_check.getOrAwaitValue
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.toGitHubDataClass
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
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class RepoDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: RepoDetailsViewModel

    @Mock
    private lateinit var localGitHubRepository: LocalGitHubRepository

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var localDBResponseObserver: Observer<LocalDBQueryResponse>

    @Mock
    private lateinit var gitRepoDataObserver: Observer<GitHubRepoObject>

    @Mock
    private lateinit var favouriteStatusObserver: Observer<Boolean>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = RepoDetailsViewModel(localGitHubRepository)
        viewModel.localDBResponse.observeForever(localDBResponseObserver)
        viewModel.favouriteStatus.observeForever(favouriteStatusObserver)
        viewModel.gitRepoData.observeForever(gitRepoDataObserver)
    }

    /**
     * Test case for setGitRepoData()
     */
    @Test
    fun `test set value to the live data using setGitRepoData`() {

        // When setting the GitHubRepoObject in the ViewModel
        viewModel.setGitRepoData(mockGitHubRepoObject)

        // Then the LiveData value should be updated
        val result = viewModel.gitRepoData.getOrAwaitValue()
        assertEquals(expectedGitHubRepoObject, result)
    }

    /**
     * Test case for addToFavourites()
     */
    @Test
    fun `test add item to local db using addToFavourites`() = runBlocking {

        // When setting the GitHubRepoObject in the ViewModel
        viewModel.setGitRepoData(mockGitHubRepoObject)

        // Then the LiveData value should be updated
        val result = viewModel.gitRepoData.getOrAwaitValue()

        val mockLocalGitHubRepoObject = result.toGitHubDataClass()

        // Mock the behavior of your repository
        `when`(localGitHubRepository.insertGitHubObject(mockLocalGitHubRepoObject)).thenReturn(
            LocalDBQueryResponse(
                true,
                MessageConstants.getMessage(MessageConstants.FAV_ADDED_SUCCESS_CODE)
            )
        ) // Adjust based on your data classes and response structure.

        // Call the function
        viewModel.addToFavourites()

        viewModel.localDBResponse.getOrAwaitValue()
        verify(localDBResponseObserver).onChanged(
            LocalDBQueryResponse(
                true,
                MessageConstants.getMessage(MessageConstants.FAV_ADDED_SUCCESS_CODE)
            )
        )
        assertEquals(true, viewModel.favouriteStatus.value)
    }

    @Test
    fun `test delete item from local db using deleteFavourite`() = runBlocking {

        // Mock the behavior of your repository
        `when`(localGitHubRepository.deleteGitHubObjectDao(1)).thenReturn(
            LocalDBQueryResponse(
                true,
                MessageConstants.getMessage(MessageConstants.FAV_DELETE_SUCCESS_CODE)
            )
        ) // Adjust based on your data classes and response structure.

        // Call the function
        viewModel.deleteFavourite(1)

        viewModel.localDBResponse.getOrAwaitValue()
        verify(localDBResponseObserver).onChanged(
            LocalDBQueryResponse(
                true,
                MessageConstants.getMessage(MessageConstants.FAV_DELETE_SUCCESS_CODE)
            )
        )
        assertEquals(false, viewModel.favouriteStatus.value)
    }

    /**
     * Test case for setGitRepoData()
     */
    @Test
    fun `test set value to the favouriteStatus live data using checkFavStatus`() {
        viewModel.checkFavStatus(true)
        val result = viewModel.favouriteStatus.getOrAwaitValue()
        assertEquals(true, result)
    }

    @After
    fun tearDown() {
        viewModel.localDBResponse.removeObserver(localDBResponseObserver)
        viewModel.favouriteStatus.removeObserver(favouriteStatusObserver)
        viewModel.gitRepoData.removeObserver(gitRepoDataObserver)
        Dispatchers.resetMain()
    }
}