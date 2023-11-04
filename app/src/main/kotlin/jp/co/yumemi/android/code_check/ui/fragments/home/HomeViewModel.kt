package jp.co.yumemi.android.code_check.ui.fragments.home

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.constants.MessageConstants
import jp.co.yumemi.android.code_check.constants.MessageConstants.NO_INTERNET_ERROR_CODE
import jp.co.yumemi.android.code_check.constants.MessageConstants.SEARCH_VIEW_VALUE_EMPTY_ERROR_CODE
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import jp.co.yumemi.android.code_check.utils.NetworkUtils
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Home Fragment View Model
 */
@HiltViewModel
class HomeViewModel @Inject constructor(private val gitHubRepository: GitHubRepository) :
    ViewModel() {

    private val _gitHubRepoList = MutableLiveData<List<GitHubRepoObject>>()
    val gitHubRepoList: LiveData<List<GitHubRepoObject>> get() = _gitHubRepoList

    //Error Message Live Data
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    //Dialog Visibility Live Data
    private val _isDialogVisible = MutableLiveData<Boolean>()
    val isDialogVisible: LiveData<Boolean> get() = _isDialogVisible

    private val _isSearchResultsEmpty = MutableLiveData<Boolean>()
    val isSearchResultsEmpty: LiveData<Boolean> get() = _isSearchResultsEmpty

    /**
     * Get Server Response and Set values to live data
     * @param inputText Pass entered value
     */
    fun getGitHubRepoList(inputText: String) {
        if (NetworkUtils.isNetworkAvailable()) {
            //Show Progress Dialog when click on the search view submit button
            _isDialogVisible.value = true
            /* View Model Scope - Coroutine */
            viewModelScope.launch {
                val apiResponse = gitHubRepository.getRepositories(inputText)

                if (apiResponse.success) {
                    _gitHubRepoList.value = apiResponse.items
                    _isSearchResultsEmpty.value = apiResponse.items.isEmpty()
                } else
                    _errorMessage.value = apiResponse.message

                /* Hide Progress Dialog after fetching the data list from the server */
                _isDialogVisible.value = false
            }
        } else {
            _errorMessage.value = MessageConstants.getMessage(NO_INTERNET_ERROR_CODE)
        }
    }

    /**
     * Search View Submit Button Click Event
     */
    fun onEditorAction(editeText: TextView?, actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {

            val enteredValue = editeText?.text.toString()

            if (enteredValue.isNullOrBlank()) {
                //Empty value error Alert
                _errorMessage.value =
                    MessageConstants.getMessage(SEARCH_VIEW_VALUE_EMPTY_ERROR_CODE)
            } else {
                getGitHubRepoList(editeText?.text.toString())
            }
            return true
        }
        return false
    }


}