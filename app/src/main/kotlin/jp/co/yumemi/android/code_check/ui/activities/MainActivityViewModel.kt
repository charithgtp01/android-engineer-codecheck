package jp.co.yumemi.android.code_check.ui.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Settings Fragment View Model
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor() : ViewModel() {

    private val _updateBottomMenuStatus = MutableLiveData<Boolean?>(null)
    val updateBottomMenuStatus: LiveData<Boolean?> get() = _updateBottomMenuStatus

    fun setUpdateBottomMenuStatus(isUpdateStatus: Boolean) {
        _updateBottomMenuStatus.value = isUpdateStatus;
    }

}