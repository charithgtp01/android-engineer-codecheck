package jp.co.yumemi.android.code_check.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.constants.StringConstants.HOME_FRAGMENT
import javax.inject.Inject

/**
 * ViewModel for the main activity of your application.
 *
 * This ViewModel is responsible for managing the state and data related to the main activity,
 * including the currently displayed fragment, the status of the bottom menu, expanded states,
 * and whether search results are empty.
 *
 * @property _fragment The MutableLiveData that stores the currently displayed fragment.
 * @property fragment The LiveData property for accessing the currently displayed fragment.
 * @property _fragmentName The MutableLiveData that stores the currently page header.
 * @property fragmentName The LiveData property for accessing the currently displayed page header.
 * @property _updateBottomMenuStatus The MutableLiveData for updating the status of the bottom menu.
 * @property updateBottomMenuStatus The LiveData property for observing changes in the bottom menu status.
 * @property expandedStates A mutable map that stores the expanded states of items with unique IDs.
 * @property _isSearchResultsEmpty The MutableLiveData for tracking whether search results are empty.
 * @property isSearchResultsEmpty The LiveData property for observing changes in search results emptiness.
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor() :
    ViewModel() {

    private val _fragment = MutableLiveData(HOME_FRAGMENT)
    val fragment get() = _fragment

    private val _fragmentName = MutableLiveData<String?>(null)
    val fragmentName get() = _fragmentName

    private val _updateBottomMenuStatus = MutableLiveData<Boolean?>(null)
    val updateBottomMenuStatus: LiveData<Boolean?> get() = _updateBottomMenuStatus

    val expandedStates = mutableMapOf<Long, Boolean>()

    private val _isSearchResultsEmpty = MutableLiveData<Boolean>(null)
    val isSearchResultsEmpty get() = _isSearchResultsEmpty

    /**
     * Sets the update status of the bottom menu.
     *
     * @param isUpdateStatus A boolean indicating whether the bottom menu status should be updated.
     */
    fun setUpdateBottomMenuStatus(isUpdateStatus: Boolean) {
        _updateBottomMenuStatus.value = isUpdateStatus
    }

    /**
     * Sets the currently displayed fragment name.
     *
     * @param fragmentName The string identifier of the fragment name.
     */
    fun setFragmentName(fragmentName: String) {
        _fragmentName.value = fragmentName
    }

    /**
     * Sets the currently displayed fragment.
     *
     * @param fragment The string identifier of the fragment.
     */
    fun setFragment(fragment: String) {
        _fragment.value = fragment
    }

    /**
     * Sets whether search results are empty and controls the display of an empty data image.
     *
     * @param shouldShow A boolean indicating whether the empty data image should be displayed.
     */
    fun setEmptyDataImage(shouldShow: Boolean) {
        _isSearchResultsEmpty.value = shouldShow
    }
}