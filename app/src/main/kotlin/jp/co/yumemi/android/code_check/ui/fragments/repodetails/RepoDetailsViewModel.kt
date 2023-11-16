package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.SingleLiveEvent
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.toGitHubDataClass
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the GitHub Repository Details View.
 *
 * This ViewModel is responsible for managing and providing data related to a selected GitHub
 * repository's details. It interacts with the local database to support favouring and remove favouring
 * repositories.
 *
 * @property localGitHubRepository An instance of [LocalGitHubRepository] for local database operations.
 * @property localDBResponse LiveData to observe responses from local database queries.
 * @property gitRepoData LiveData containing the selected GitHub repository's data.
 * @property favouriteStatus LiveData indicating the favourite status of the selected repository.
 * @property btnValue LiveData indicating the text to be set on a button.
 * @property startsLabel LiveData indicating the text to be set for the starts label.
 * @property forksLabel LiveData indicating the text to be set for the forks label.
 * @property watchersLabel LiveData indicating the text to be set for the watchers label.
 * @property openIssuesLabel LiveData indicating the text to be set for the open issues label.
 * @property language LiveData indicating the programming language associated with the repository.
 */
@HiltViewModel
class RepoDetailsViewModel @Inject constructor(private val localGitHubRepository: LocalGitHubRepository) :
    ViewModel() {

    //SingleLiveEvent is a custom LiveData that only delivers events once.
    private val _localDBResponse = SingleLiveEvent<LocalDBQueryResponse>()
    val localDBResponse get() = _localDBResponse

    private val _gitRepoData = MutableLiveData<GitHubRepoObject>(null)
    val gitRepoData get() = _gitRepoData

    private val _favouriteStatus = MutableLiveData(false)
    val favouriteStatus get() = _favouriteStatus

    private val _btnValue = MutableLiveData<String>(null)
    val btnValue get() = _btnValue

    private val _startsLabel = MutableLiveData<String>(null)
    val startsLabel get() = _startsLabel

    private val _forksLabel = MutableLiveData<String>(null)
    val forksLabel get() = _forksLabel

    private val _watchersLabel = MutableLiveData<String>(null)
    val watchersLabel get() = _watchersLabel

    private val _openIssuesLabel = MutableLiveData<String>(null)
    val openIssuesLabel get() = _openIssuesLabel

    private val _languageString = MutableLiveData<String>(null)
    val languageString get() = _languageString

    /**
     * Set the selected GitHub repository data to LiveData.
     *
     * @param gitHubRepo The GitHub repository object to display in the details view.
     */
    fun setGitRepoData(gitHubRepo: GitHubRepoObject) {
        _gitRepoData.value = gitHubRepo
    }

    /**
     * Updates the UI with the specified values.
     *
     * This function is responsible for updating various UI elements with the provided values,
     * such as button text, start labels, forks labels, watchers labels, open issues labels, and language.
     *
     * @param btnValue The text to be set on a button.
     * @param startsLabel The text to be set for the starts label.
     * @param forksLabel The text to be set for the forks label.
     * @param watchersLabel The text to be set for the watchers label.
     * @param openIssuesLabel The text to be set for the open issues label.
     * @param language The programming language associated with the repository.
     */
    fun updateUI(
        btnValue: String,
        startsLabel: String,
        forksLabel: String,
        watchersLabel: String,
        openIssuesLabel: String,
        language: String
    ) {
        _btnValue.value = btnValue
        _startsLabel.value = startsLabel
        _forksLabel.value = forksLabel
        _watchersLabel.value = watchersLabel
        _openIssuesLabel.value = openIssuesLabel
        _languageString.value = "$language : ${gitRepoData.value?.language}"
    }

    /**
     * When click on fav button from the HomeFragment
     * Adds the selected GitHub account to favorites in the local database.
     */
    fun addToFavourites() {
        val gitHubRepoObject = gitRepoData.value

        if (gitHubRepoObject != null) {
            viewModelScope.launch {
                val gitHubDataClass = gitHubRepoObject.toGitHubDataClass()
                val response = localGitHubRepository.insertGitHubObject(gitHubDataClass)
                response.apply {
                    _localDBResponse.value = this
                    when {
                        success -> _favouriteStatus.value = true
                    }
                }
            }
        }
    }

    /**
     * Deletes the selected GitHub account from favorites in the local database.
     *
     * @param id The unique identifier of the GitHub repository to non favourite.
     */
    fun deleteFavourite(id: Long) {
        viewModelScope.launch {
            val response = localGitHubRepository.deleteGitHubObjectDao(id)
            response.also {
                _localDBResponse.value = it
                when {
                    it.success -> _favouriteStatus.value = false
                }
            }
        }
    }

    /**
     * Sets the favorite status of the selected GitHub repository.
     *
     * @param isFavourite `true` if the repository is favourite, `false` otherwise.
     */
    fun checkFavStatus(isFavourite: Boolean) {
        _favouriteStatus.value = isFavourite
    }

}