package jp.co.yumemi.android.code_check.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.BR
import jp.co.yumemi.android.code_check.databinding.LayoutRepoListItemBinding
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import javax.inject.Inject

/**
 * Adapter for displaying a list of GitHub repositories.
 *
 * This adapter is designed to work with a [RecyclerView] and uses data binding with
 * [LayoutRepoListItemBinding] to efficiently display a list of GitHub repositories.
 * It includes functionality for handling click events on items and displaying favorite
 * repositories based on a separate list of [LocalGitHubRepoObject].
 *
 * @constructor Creates a [RepoListAdapter] with the provided [itemClickListener].
 *
 * @param itemClickListener Listener for item click events in the RecyclerView.
 */
class RepoListAdapter @Inject constructor(
    private val itemClickListener: OnItemClickListener
) :
    ListAdapter<GitHubRepoObject, RepoListAdapter.RepoListViewHolder>(diffUtil) {

    private var favoriteItems: List<LocalGitHubRepoObject>? = null

    /**
     * Interface to handle click events on GitHub repository items.
     */
    interface OnItemClickListener {
        /**
         * Called when a GitHub repository item is clicked.
         *
         * @param item Clicked [GitHubRepoObject].
         * @param isFavorite Boolean indicating whether the item is marked as a favorite.
         */
        fun itemClick(item: GitHubRepoObject, isFavorite: Boolean)
    }

    inner class RepoListViewHolder(val binding: LayoutRepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: GitHubRepoObject) {
            binding.apply {
                setVariable(BR.item, obj)
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRepoListItemBinding.inflate(inflater, parent, false)
        return RepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        val repoObject = getItem(position)

        //Check the item is available in saved github list
        val isFavorite: Boolean = favoriteItems?.any { it.id == repoObject.id } == true

        holder.apply {
            bind(repoObject)
            binding.apply {
                root.setOnClickListener {
                    itemClickListener.itemClick(repoObject, isFavorite)
                }
                favIcon.isVisible = isFavorite
            }
        }
    }

    /**
     * Sets the list of favorite repositories to be displayed alongside the main repository list.
     *
     * @param repoList List of [LocalGitHubRepoObject] representing favorite repositories.
     */
    fun setFavourites(repoList: List<LocalGitHubRepoObject>) {
        favoriteItems = repoList
    }
}

/**
 * DiffUtil callback for calculating the difference between two lists of [GitHubRepoObject].
 */
val diffUtil = object : DiffUtil.ItemCallback<GitHubRepoObject>() {
    /**
     * Checks if the two items represent the same object in the list.
     *
     * @param oldItem The old [GitHubRepoObject].
     * @param newItem The new [GitHubRepoObject].
     * @return `true` if items represent the same object, `false` otherwise.
     */
    override fun areItemsTheSame(oldItem: GitHubRepoObject, newItem: GitHubRepoObject): Boolean {
        return oldItem.name == newItem.name
    }

    /**
     * Checks if the contents of the two items are the same.
     *
     * @param oldItem The old [GitHubRepoObject].
     * @param newItem The new [GitHubRepoObject].
     * @return `true` if contents are the same, `false` otherwise.
     */
    override fun areContentsTheSame(oldItem: GitHubRepoObject, newItem: GitHubRepoObject): Boolean {
        return oldItem == newItem
    }
}
