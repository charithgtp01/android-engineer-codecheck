package jp.co.yumemi.android.code_check

import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.Owner

class MockObjects {
    companion object{
         val mockOwnerObj =
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
        val mockData = listOf(mockGitHubRepoObject)

         val expectedOwnerObj =
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
         val expectedData = listOf(expectedGitHubRepoObject)
    }
}