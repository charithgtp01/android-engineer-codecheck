package jp.co.yumemi.android.code_check.ui.fragments.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.yumemi.android.code_check.repository.LocalGitHubRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * FavouritsViewModel class for managing favourite GitHub repositories.
 *
 * @param repository The [LocalGitHubRepository] responsible for handling local database operations.
 */
@HiltViewModel
class FavouritesViewModel @Inject constructor(val repository: LocalGitHubRepository) : ViewModel() {

    /**
     * LiveData representing a list of all favourite GitHub repositories from the local database.
     */
    val allFavourites = repository.getAllRepositories()

    /**
     * Deletes a favourite GitHub repository from the local database.
     *
     * @param id The unique identifier of the GitHub repository to be deleted.
     */
    fun deleteFavourite(id: Long) {
        viewModelScope.launch {
            repository.deleteGitHubObjectDao(id)
        }
    }

}