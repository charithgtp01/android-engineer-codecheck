package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.toGitHubDataClass
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Home Fragment View Model
 */
@HiltViewModel
class RepoDetailsViewModel @Inject constructor(private val localGitHubRepository: LocalGitHubRepository) : ViewModel() {

    private val _localDBResponse = MutableLiveData<LocalDBQueryResponse>()
    val localDBResponse: LiveData<LocalDBQueryResponse> get() = _localDBResponse


    private val _gitRepoData = MutableLiveData<GitHubRepoObject>(null)
    val gitRepoData: LiveData<GitHubRepoObject> get() = _gitRepoData

    /**
     * Set Git Hub Object to Live Data
     * @param Selected Git Hub Repo Object
     */
    fun setGitRepoData(gitHubRepo: GitHubRepoObject) {
        _gitRepoData.value = gitHubRepo
    }

    /**
     * When click on fav button from the HomeFragment
     * Selected Git Hub account adding to the fav local DB
     */
    fun addToFavourites() {
        val gitHubRepoObject = gitRepoData.value

        if (gitHubRepoObject != null) {
            viewModelScope.launch {
                val gitHubDataClass = gitHubRepoObject.toGitHubDataClass()
                _localDBResponse.value = localGitHubRepository.insertGitHubObject(gitHubDataClass)
            }
        }
    }

}