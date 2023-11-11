package jp.co.yumemi.android.code_check.ui.fragments.home

import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import jp.co.yumemi.android.code_check.MockObjects
import jp.co.yumemi.android.code_check.constants.MessageConstants.NO_INTERNET_ERROR_CODE
import jp.co.yumemi.android.code_check.constants.MessageConstants.SEARCH_VIEW_VALUE_EMPTY_ERROR_CODE
import jp.co.yumemi.android.code_check.constants.MessageConstants.getMessage
import jp.co.yumemi.android.code_check.getOrAwaitValue
import jp.co.yumemi.android.code_check.models.ApiResponse
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import jp.co.yumemi.android.code_check.utils.NetworkUtils
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class HomeViewModelTest {
    private val testDispatcher = TestCoroutineDispatcher()

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var gitHubRepository: GitHubRepository

    @Mock
    private lateinit var gitHubRepoObserver: Observer<List<GitHubRepoObject>>

    @Mock
    private lateinit var errorMessageObserver: Observer<String>

    @Mock
    private lateinit var dialogObserver: Observer<Boolean>

    @Mock
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkUtils: NetworkUtils
    private lateinit var networkInfo: NetworkInfo

    private val successServerResponse =
        ApiResponse(
            success = true,
            message = "Data fetched Successfully",
            items = MockObjects.mockData
        )

    private val errorServerResponse =
        ApiResponse(success = false, message = "Error From Server", items = listOf())

    private val responseErrorMessage = "Error From Server"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)

        NetworkUtils.init(connectivityManager)
        networkUtils = NetworkUtils()
        networkInfo = mock(NetworkInfo::class.java)
        viewModel = HomeViewModel(gitHubRepository)
        viewModel.gitHubRepoList.observeForever(gitHubRepoObserver)
        viewModel.errorMessage.observeForever(errorMessageObserver)
        viewModel.isDialogVisible.observeForever(dialogObserver)

    }

    @Test
    fun `test get repo list success response`() = runBlocking {
        // Arrange
        val inputText = "example"


        `when`(networkInfo.isConnected).thenReturn(true)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
        `when`(gitHubRepository.getRepositories(inputText))
            .thenReturn(successServerResponse)

        // Act
        viewModel.getGitHubRepoList(inputText)

        // Assert
        // Verify that the gitHubRepoList LiveData is updated with the expected data
        verify(gitHubRepoObserver).onChanged(MockObjects.expectedData)
        // Verify that the dialog visibility is updated
        verify(dialogObserver).onChanged(false)
    }

    @Test
    fun `test get repo list error response`() = runBlocking {
        // Arrange
        val inputText = "example"
        `when`(networkInfo.isConnected).thenReturn(true)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

        `when`(gitHubRepository.getRepositories(inputText))
            .thenReturn(errorServerResponse)
        // Act
        viewModel.getGitHubRepoList(inputText)

        // Wait for LiveData changes
        val result = viewModel.errorMessage.getOrAwaitValue()
        verify(errorMessageObserver).onChanged(responseErrorMessage)
        // Verify that the dialog visibility is updated
        verify(dialogObserver).onChanged(false)
    }

    @Test
    fun `test network unavailability`() {
        // Arrange
        val inputText = "example"

        `when`(networkInfo.isConnected).thenReturn(false)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)

        // Act
        viewModel.getGitHubRepoList(inputText)

        // Assert
        // Verify that the errorMessage LiveData is updated with the expected error message
        verify(errorMessageObserver).onChanged(getMessage(NO_INTERNET_ERROR_CODE))
    }

    @Test
    fun `test onEditorAction with search action`() {
        // Arrange
        val editText = mock(TextView::class.java)
        val actionId = EditorInfo.IME_ACTION_SEARCH
        val enteredValue = "charithvin"
        `when`(editText.text).thenReturn(enteredValue)
        // Act
        val searchViewResult = viewModel.onEditorAction(editText, actionId)

        // Verify that the method returns true (IME_ACTION_SEARCH)
        assertTrue(searchViewResult)
    }

    @Test
    fun `test onEditorAction when action is search and text not empty calls getGitHubRepoList method`() =
        runBlocking {
            // Arrange
            val editText = mock(TextView::class.java)
            val actionId = EditorInfo.IME_ACTION_SEARCH
            val enteredValue = "charith"

            `when`(editText.text).thenReturn(enteredValue)
            `when`(networkInfo.isConnected).thenReturn(true)
            `when`(connectivityManager.activeNetworkInfo).thenReturn(networkInfo)
            `when`(gitHubRepository.getRepositories(enteredValue))
                .thenReturn(successServerResponse)
            // Act
            val searchViewResult = viewModel.onEditorAction(editText, actionId)

            // Verify that the method returns true (IME_ACTION_SEARCH)
            assertTrue(searchViewResult)

            viewModel.getGitHubRepoList(enteredValue)
            // Wait for LiveData changes
            val result = viewModel.gitHubRepoList.getOrAwaitValue()
            assertEquals(successServerResponse.items, result)
        }

    @Test
    fun `test onEditorAction with blank text sets error message`() {
        // Arrange
        val editText = mock(TextView::class.java)
        val actionId = EditorInfo.IME_ACTION_SEARCH
        val enteredValue = ""

        `when`(editText.text).thenReturn(enteredValue)

        // Act
        val searchViewResult = viewModel.onEditorAction(editText, actionId)

        // Verify that the method returns true (IME_ACTION_SEARCH)
        assertTrue(searchViewResult)

        assertEquals(
            getMessage(SEARCH_VIEW_VALUE_EMPTY_ERROR_CODE),
            viewModel.errorMessage.value
        )
    }


    @After
    fun tearDown() {
        viewModel.gitHubRepoList.removeObserver(gitHubRepoObserver)
        viewModel.isDialogVisible.removeObserver(dialogObserver)
        viewModel.errorMessage.removeObserver(errorMessageObserver)
        Dispatchers.resetMain()
    }
}