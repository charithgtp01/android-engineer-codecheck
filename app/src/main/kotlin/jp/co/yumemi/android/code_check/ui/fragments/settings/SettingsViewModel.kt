package jp.co.yumemi.android.code_check.ui.fragments.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.constants.StringConstants
import javax.inject.Inject

/**
 * Settings Fragment View Model
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _selectedLanguage = MutableLiveData<String?>(null)
    val selectedLanguage: LiveData<String?> get() = _selectedLanguage

    private val _shouldSelectJapanese = MutableLiveData<Boolean?>(null)
    val shouldSelectJapanese: LiveData<Boolean?> get() = _shouldSelectJapanese

    private val _shouldSelectEnglish = MutableLiveData<Boolean?>(null)
    val shouldSelectEnglish: LiveData<Boolean?> get() = _shouldSelectEnglish


    fun setSelectedLanguage(language: String?) {
        _selectedLanguage.value = language
        if (language.equals(StringConstants.JAPANESE.value)) {
            _shouldSelectJapanese.value = true
            _shouldSelectEnglish.value = false
        } else {
            _shouldSelectJapanese.value = false
            _shouldSelectEnglish.value = true
        }
    }

    fun onEnglishLayoutClicked() {
        // Handle the click event for the English layout
        _selectedLanguage.value = StringConstants.ENGLISH.value
        _shouldSelectJapanese.value = false
        _shouldSelectEnglish.value = true
    }

    fun onJapaneseLayoutClicked() {
        // Handle the click event for the Japanese layout
        _selectedLanguage.value = StringConstants.JAPANESE.value
        _shouldSelectJapanese.value = true
        _shouldSelectEnglish.value = false
    }
}