package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.Owner
import jp.co.yumemi.android.code_check.ui.fragments.home.getOrAwaitValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepoDetailsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: RepoDetailsViewModel
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

    private val expectedOwnerObj =
        Owner(avatarUrl = "https://avatars.githubusercontent.com/u/22025488?v=4", type = "User")
    private val expectedGitHubRepoObject = GitHubRepoObject(
        id = 1,
        name = "charithvin",
        owner = expectedOwnerObj,
        nullableLanguage = "CSS",
        stargazersCount = 10,
        watchersCount = 15,
        forksCount = 4,
        openIssuesCount = 25
    )

    @Before
    fun setup() {
        viewModel = RepoDetailsViewModel()
    }

    @Test
    fun `test set value to the live data using setGitRepoData`() {

        // When setting the GitHubRepoObject in the ViewModel
        viewModel.setGitRepoData(mockGitHubRepoObject)

        // Then the LiveData value should be updated
        val result = viewModel.gitRepoData.getOrAwaitValue()
        assertEquals(expectedGitHubRepoObject, result)
    }
}