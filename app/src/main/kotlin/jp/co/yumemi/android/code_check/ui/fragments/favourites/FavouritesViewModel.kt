package jp.co.yumemi.android.code_check.ui.fragments.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import javax.inject.Inject

/**
 * Settings Fragment View Model
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(repository: LocalGitHubRepository) : ViewModel() {
    val allFavourites: LiveData<List<LocalGitHubRepoObject>> = repository.getAllRepositories()
}