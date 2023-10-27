package jp.co.yumemi.android.code_check.ui.fragments.home

import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.constants.MessageConstants
import jp.co.yumemi.android.code_check.constants.MessageConstants.NO_INTERNET
import jp.co.yumemi.android.code_check.constants.MessageConstants.SEARCH_VIEW_VALUE_EMPTY_ERROR
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
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    /**
     * Get Server Response and Set values to live data
     * @param inputText Pass entered value
     */
    private fun getGitHubRepoList(inputText: String) {
        if (NetworkUtils.isNetworkAvailable()) {
            /* View Model Scope - Coroutine */
            viewModelScope.launch {
                val resource = gitHubRepository.getRepositories(inputText)

                if (resource?.data != null) {
                    _gitHubRepoList.value = resource.data.items
                } else
                    _errorMessage.value = resource?.error?.error
            }
        } else {
            _errorMessage.value = NO_INTERNET
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
                _errorMessage.value = SEARCH_VIEW_VALUE_EMPTY_ERROR
            } else {
                getGitHubRepoList(editeText?.text.toString())
            }


            return true
        }
        return false
    }
}