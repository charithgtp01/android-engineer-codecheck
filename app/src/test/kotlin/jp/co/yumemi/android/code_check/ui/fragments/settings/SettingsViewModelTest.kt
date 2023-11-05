package jp.co.yumemi.android.code_check.ui.fragments.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class SettingsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var selectedLanguageObserver: Observer<String?>

    @Mock
    private lateinit var shouldSelectJapaneseObserver: Observer<Boolean?>

    @Mock
    private lateinit var shouldSelectEnglishObserver: Observer<Boolean?>

    private lateinit var viewModel: SettingsViewModel

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = SettingsViewModel()
        viewModel.selectedLanguage.observeForever(selectedLanguageObserver)
        viewModel.shouldSelectJapanese.observeForever(shouldSelectJapaneseObserver)
        viewModel.shouldSelectEnglish.observeForever(shouldSelectEnglishObserver)
    }

    @Test
    fun `test setSelectedLanguage Japanese`() = runBlocking {
        // Call the method to set the selected language
        viewModel.setSelectedLanguage("日本語")

        // Verify that the selectedLanguage and shouldSelectJapanese/shouldSelectEnglish were updated as expected
        Mockito.verify(selectedLanguageObserver).onChanged("日本語")
        Mockito.verify(shouldSelectJapaneseObserver).onChanged(true)
        Mockito.verify(shouldSelectEnglishObserver).onChanged(false)
    }

    @Test
    fun `test setSelectedLanguage English`() = runBlocking {
        // Call the method to set the selected language
        viewModel.setSelectedLanguage("English")

        // Verify that the selectedLanguage and shouldSelectJapanese/shouldSelectEnglish were updated as expected
        Mockito.verify(selectedLanguageObserver).onChanged("English")
        Mockito.verify(shouldSelectJapaneseObserver).onChanged(false)
        Mockito.verify(shouldSelectEnglishObserver).onChanged(true)
    }


    @Test
    fun `test onEnglishLayoutClicked`() {
        // Call the method to simulate a click event for the English layout
        viewModel.onEnglishLayoutClicked()

        // Verify that the selectedLanguage and shouldSelectJapanese/shouldSelectEnglish were updated as expected
        Mockito.verify(selectedLanguageObserver).onChanged("English")
        Mockito.verify(shouldSelectJapaneseObserver).onChanged(false)
        Mockito.verify(shouldSelectEnglishObserver).onChanged(true)
    }

    @Test
    fun `test onJapaneseLayoutClicked`() {
        // Call the method to simulate a click event for the Japanese layout
        viewModel.onJapaneseLayoutClicked()

        // Verify that the selectedLanguage and shouldSelectJapanese/shouldSelectEnglish were updated as expected
        Mockito.verify(selectedLanguageObserver).onChanged("日本語")
        Mockito.verify(shouldSelectJapaneseObserver).onChanged(true)
        Mockito.verify(shouldSelectEnglishObserver).onChanged(false)
    }

    @After
    fun tearDown() {
        viewModel.selectedLanguage.removeObserver(selectedLanguageObserver)
        viewModel.shouldSelectJapanese.removeObserver(shouldSelectJapaneseObserver)
        viewModel.shouldSelectEnglish.removeObserver(shouldSelectEnglishObserver)
        Dispatchers.resetMain()
    }
}