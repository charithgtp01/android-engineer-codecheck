package jp.co.yumemi.android.code_check

import jp.co.yumemi.android.code_check.models.ApiResponse
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.models.Owner

class MockObjects {
    companion object {
        private val mockOwnerObj =
            Owner(avatarUrl = "https://avatars.githubusercontent.com/u/22025488?v=4", type = "User")
        val mockGitHubRepoObject = GitHubRepoObject(
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

        private val expectedOwnerObj =
            Owner(avatarUrl = "https://avatars.githubusercontent.com/u/22025488?v=4", type = "User")
        val expectedGitHubRepoObject = GitHubRepoObject(
            id = 1,
            name = "charithvin",
            owner = expectedOwnerObj,
            nullableLanguage = "CSS",
            stargazersCount = 10,
            watchersCount = 15,
            forksCount = 4,
            openIssuesCount = 25
        )

        //Local DB Data
        private val mockFavObject = LocalGitHubRepoObject(
            id = 1,
            name = "charithvin",
            avatarUrl = "https://avatars.githubusercontent.com/u/22025488?v=4",
            ownerType = "User",
            language = "CSS",
            stargazersCount = 10,
            watchersCount = 15,
            forksCount = 4,
            openIssuesCount = 25
        )
        val mockFavList = listOf(mockFavObject)

        private val expectedFavObject = LocalGitHubRepoObject(
            id = 1,
            name = "charithvin",
            avatarUrl = "https://avatars.githubusercontent.com/u/22025488?v=4",
            ownerType = "User",
            language = "CSS",
            stargazersCount = 10,
            watchersCount = 15,
            forksCount = 4,
            openIssuesCount = 25
        )
        val expectedFavList = listOf(expectedFavObject)

        val successServerResponse =
            ApiResponse(
                success = true,
                message = "Data fetched Successfully",
                items = mockData
            )

        val errorServerResponse =
            ApiResponse(success = false, message = "Error From Server", items = null)

        const val responseErrorMessage = "Error From Server"
    }
}