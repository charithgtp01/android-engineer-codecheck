package jp.co.yumemi.android.code_check.ui.fragments.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.constants.StringConstants
import javax.inject.Inject

/**
 * ViewModel class for managing settings related to language selection in the application.
 *
 * @property _selectedLanguage MutableLiveData holding the selected language.
 * @property selectedLanguage LiveData exposing the selected language to observers.
 * @property _shouldSelectJapanese MutableLiveData indicating whether Japanese language should be selected.
 * @property shouldSelectJapanese LiveData exposing the decision to select Japanese language to observers.
 * @property _shouldSelectEnglish MutableLiveData indicating whether English language should be selected.
 * @property shouldSelectEnglish LiveData exposing the decision to select English language to observers.
 * @property _selectedLanguageLabel MutableLiveData holding the label associated with the selected language.
 * @property selectedLanguageLabel LiveData exposing the selected language label to observers.
 */
@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    private val _selectedLanguage = MutableLiveData<String?>(null)
    val selectedLanguage get() = _selectedLanguage

    private val _shouldSelectJapanese = MutableLiveData<Boolean?>(null)
    val shouldSelectJapanese get() = _shouldSelectJapanese

    private val _shouldSelectEnglish = MutableLiveData<Boolean?>(null)
    val shouldSelectEnglish get() = _shouldSelectEnglish

    private val _selectedLanguageLabel = MutableLiveData<String?>(null)
    val selectedLanguageLabel get() = _selectedLanguageLabel

    /**
     * Sets the selected language and updates related LiveData values.
     *
     * @param language The code representing the selected language.
     */
    fun setSelectedLanguage(language: String?) {
        _selectedLanguage.value = language
        run {
            _shouldSelectJapanese.value = language.equals(StringConstants.JAPANESE)
            _shouldSelectEnglish.value = !language.equals(StringConstants.JAPANESE)
        }
    }

    /**
     * Sets the label associated with the selected language.
     *
     * @param label The label to be associated with the selected language.
     */
    fun setSelectedLanguageLabel(label: String?) {
        _selectedLanguageLabel.value = label
    }

    /**
     * Handles the click event for the English layout, setting the selected language to English.
     */
    fun onEnglishLayoutClicked() {
        // Handle the click event for the English layout
        _selectedLanguage.value = StringConstants.ENGLISH
        _shouldSelectJapanese.value = false
        _shouldSelectEnglish.value = true
    }

    /**
     * Handles the click event for the Japanese layout, setting the selected language to Japanese.
     */
    fun onJapaneseLayoutClicked() {
        // Handle the click event for the Japanese layout
        _selectedLanguage.value = StringConstants.JAPANESE
        _shouldSelectJapanese.value = true
        _shouldSelectEnglish.value = false
    }
}