package jp.co.yumemi.android.code_check.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.SingleLiveEvent
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing data and business logic related to the home screen.
 *
 * This class provides methods and LiveData objects to interact with GitHub repository data and manage
 * the user interface state.
 *
 * @param gitHubRepository The repository responsible for fetching GitHub data.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val gitHubRepository: GitHubRepository) :
    ViewModel() {

    //Search View Hint Hint
    private val _searchViewHint = MutableLiveData<String>(null)
    val searchViewHint: LiveData<String> get() = _searchViewHint

    //User entered text
    var searchViewText: String? = null

    //Git Hub Repo List from the server
    private val _gitHubRepoList = MutableLiveData<List<GitHubRepoObject>>()
    val gitHubRepoList: LiveData<List<GitHubRepoObject>> get() = _gitHubRepoList

    //Error Message Live Data
    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    //Dialog Visibility Live Data
    private val _isDialogVisible = MutableLiveData<Boolean>()
    val isDialogVisible: LiveData<Boolean> get() = _isDialogVisible


    /**
     * Get the server response and set values to LiveData.
     *
     * This function fetches GitHub repository data based on the provided [inputText] and updates the
     * [_gitHubRepoList] LiveData with the retrieved data. It also handles errors by updating the
     * [_errorMessage] LiveData. Additionally, it manages the visibility of a progress dialog with the
     * [_isDialogVisible] LiveData.
     *
     * @param inputText The text entered by the user for repository search.
     */
    fun getGitHubRepoList(inputText: String?) {
        //Show Progress Dialog when click on the search view submit button
        _isDialogVisible.value = true
        /* View Model Scope - Coroutine */
        viewModelScope.launch {
            val apiResponse = gitHubRepository.getRepositories(inputText)

            if (apiResponse.success) {
                _gitHubRepoList.value = apiResponse.items
            } else
                _errorMessage.value = apiResponse.message

            /* Hide Progress Dialog after fetching the data list from the server */
            _isDialogVisible.value = false
        }
    }

    /**
     * Set an error message to be displayed.
     *
     * This function updates the [_errorMessage] LiveData with the provided [error] message to be
     * displayed to the user.
     *
     * @param error The error message to be displayed.
     */
    fun setErrorMessage(error: String) {
        _errorMessage.value = error
    }

    /**
     * Set a hint text for the search view.
     *
     * This function updates the [_searchViewHint] LiveData with the provided [hint] text, which serves
     * as a hint for the search view in the user interface.
     *
     * @param hint The hint text to be displayed in the search view.
     */
    fun setSearchViewHint(hint: String) {
        _searchViewHint.value = hint
    }
}