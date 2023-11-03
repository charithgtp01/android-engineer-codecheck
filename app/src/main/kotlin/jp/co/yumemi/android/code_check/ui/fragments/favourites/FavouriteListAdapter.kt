package jp.co.yumemi.android.code_check.ui.fragments.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutRepoListItemBinding
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import javax.inject.Inject
import jp.co.yumemi.android.code_check.BR
import jp.co.yumemi.android.code_check.databinding.LayoutFavListItemBinding
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject

/**
 * Git Repo List Adapter
 */
class FavouriteListAdapter @Inject constructor(private val itemClickListener: OnItemClickListener) :
    ListAdapter<LocalGitHubRepoObject, FavouriteListAdapter.FavouriteListViewHolder>(favDiffUtil) {


    /**
     * On Item Click Listener
     */
    interface OnItemClickListener {
        fun itemClick(item: LocalGitHubRepoObject)
    }

    inner class FavouriteListViewHolder(val binding: LayoutFavListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: LocalGitHubRepoObject) {
            binding.setVariable(BR.item, obj)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutFavListItemBinding.inflate(inflater, parent, false)
        return FavouriteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteListViewHolder, position: Int) {
        val repoObject = getItem(position)
        holder.bind(repoObject)
        holder.binding.root.setOnClickListener {
            itemClickListener.itemClick(repoObject)
        }
    }

}

/**
 * Diff Util Interface
 */
val favDiffUtil = object : DiffUtil.ItemCallback<LocalGitHubRepoObject>() {
    override fun areItemsTheSame(oldItem: LocalGitHubRepoObject, newItem: LocalGitHubRepoObject): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: LocalGitHubRepoObject, newItem: LocalGitHubRepoObject): Boolean {
        return oldItem == newItem
    }
}
