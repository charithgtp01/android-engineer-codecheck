package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.Owner
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RepoDetailsViewModelTest {
    private lateinit var viewModel: RepoDetailsViewModel

    @Before
    fun setup() {
        viewModel = RepoDetailsViewModel()
    }

    @Test
    fun testSetGitRepoData() {
        val mockGitHubRepo = GitHubRepoObject(
            name = "TestRepo",
            owner = Owner("test_owner", "avatar_url"),
            nullableLanguage = "Kotlin",
            stargazersCount = 10,
            watchersCount = 15,
            forksCount = 5,
            openIssuesCount = 3
        )

        viewModel.setGitRepoData(mockGitHubRepo)

        val liveDataValue = viewModel.gitRepoData.value
        assertEquals(mockGitHubRepo, liveDataValue)
    }
}