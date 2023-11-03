package jp.co.yumemi.android.code_check.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.constants.StringConstants.HOME_FRAGMENT
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.models.toGitHubDataClass
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Main Activity Shared View Model
 * Multiple Fragments within the same Activity and we need them to communicate with each other,
 * A shared ViewModel can act as a communication channel.
 * It allows one Fragment to update data or trigger actions that affect another Fragment.
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor() :
    ViewModel() {

    private val _fragment = MutableLiveData(HOME_FRAGMENT)
    val fragment: LiveData<String> get() = _fragment

    private val _updateBottomMenuStatus = MutableLiveData<Boolean?>(null)
    val updateBottomMenuStatus: LiveData<Boolean?> get() = _updateBottomMenuStatus

    val expandedStates: MutableMap<Long, Boolean> = mutableMapOf()
    private val _expandedStates = MutableLiveData<MutableMap<Long, Boolean>>(mutableMapOf())

    fun setUpdateBottomMenuStatus(isUpdateStatus: Boolean) {
        _updateBottomMenuStatus.value = isUpdateStatus;
    }

    fun setFragment(fragment: String) {
        _fragment.value = fragment
    }

}