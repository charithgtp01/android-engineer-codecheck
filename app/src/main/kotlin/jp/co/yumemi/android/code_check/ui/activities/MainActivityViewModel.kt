package jp.co.yumemi.android.code_check.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.toGitHubDataClass
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Settings Fragment View Model
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val localGitHubRepository: LocalGitHubRepository) :
    ViewModel() {

    private val _updateBottomMenuStatus = MutableLiveData<Boolean?>(null)
    val updateBottomMenuStatus: LiveData<Boolean?> get() = _updateBottomMenuStatus

    private val _selectedGitHubRepo = MutableLiveData<GitHubRepoObject>()
    val selectedGitHubRepo: LiveData<GitHubRepoObject> get() = _selectedGitHubRepo

    fun setUpdateBottomMenuStatus(isUpdateStatus: Boolean) {
        _updateBottomMenuStatus.value = isUpdateStatus;
    }

    /**
     * When click on fav button from the HomeFragment
     * Selected Git Hub account adding to the fav local DB
     */
    fun addToFavourites() {
        val gitHubRepoObject = selectedGitHubRepo.value

        if (gitHubRepoObject != null) {
            viewModelScope.launch {
                val ownerURL: String? =
                    gitHubRepoObject.owner?.avatarUrl // Extract ownerURL from the Owner object
                val gitHubDataClass = gitHubRepoObject.toGitHubDataClass(ownerURL)
                localGitHubRepository.insertGitHubObject(gitHubDataClass)
            }
        }
    }

    fun setSelectedGitHubRepo(gitHubRepo: GitHubRepoObject) {
        _selectedGitHubRepo.value = gitHubRepo
    }

}