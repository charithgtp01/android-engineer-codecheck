package jp.co.yumemi.android.code_check.ui.fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import javax.inject.Inject

/**
 * Settings Fragment View Model
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {
    private val _bgRes = MutableLiveData<Int>(null)
    val bgRes: LiveData<Int> get() = _bgRes

    fun setBgRes(res: Int) {
        _bgRes.value = res
    }


}