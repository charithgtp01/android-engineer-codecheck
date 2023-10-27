package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import javax.inject.Inject

/**
 * Home Fragment View Model
 */
class RepoDetailsViewModel @Inject constructor() : ViewModel() {
    private val _gitRepoData = MutableLiveData<GitHubRepoObject>(null)
    val gitRepoData: LiveData<GitHubRepoObject> get() = _gitRepoData

    /**
     * Set Git Hub Object to Live Data
     * @param Selected Git Hub Repo Object
     */
    fun setGitRepoData(gitHubRepo: GitHubRepoObject) {
        _gitRepoData.value = gitHubRepo
    }
}