package jp.co.yumemi.android.code_check.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.constants.StringConstants.HOME_FRAGMENT
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import javax.inject.Inject

/**
 * Main Activity Shared View Model
 * Multiple Fragments within the same Activity and we need them to communicate with each other,
 * A shared ViewModel can act as a communication channel.
 * It allows one Fragment to update data or trigger actions that affect another Fragment.
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(private val localGitHubRepository: LocalGitHubRepository) :
    ViewModel() {

    private val _fragment = MutableLiveData(HOME_FRAGMENT)
    val fragment: LiveData<String> get() = _fragment

    private val _updateBottomMenuStatus = MutableLiveData<Boolean?>(null)
    val updateBottomMenuStatus: LiveData<Boolean?> get() = _updateBottomMenuStatus

    val expandedStates: MutableMap<Long, Boolean> = mutableMapOf()

    val allFavourites: LiveData<List<LocalGitHubRepoObject>> = localGitHubRepository.getAllRepositories()

    fun setUpdateBottomMenuStatus(isUpdateStatus: Boolean) {
        _updateBottomMenuStatus.value = isUpdateStatus;
    }

    fun setFragment(fragment: String) {
        _fragment.value = fragment
    }

}