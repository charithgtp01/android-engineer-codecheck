package jp.co.yumemi.android.code_check.ui.fragments.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Settings Fragment View Model
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(val repository: LocalGitHubRepository) : ViewModel() {

    val allFavourites: LiveData<List<LocalGitHubRepoObject>> = repository.getAllRepositories()

    /**
     * When click on delete button
     * Selected Git Hub account will delete to the fav local DB
     */
    fun deleteFavourite(id: Long) {
        viewModelScope.launch {
            repository.deleteGitHubObjectDao(id)
        }
    }

}