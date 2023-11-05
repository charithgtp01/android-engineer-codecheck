package jp.co.yumemi.android.code_check.ui.fragments.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.BR
import jp.co.yumemi.android.code_check.databinding.LayoutRepoListItemBinding
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import javax.inject.Inject

/**
 * Git Repo List Adapter
 */
class RepoListAdapter @Inject constructor(
    private val itemClickListener: OnItemClickListener
) :
    ListAdapter<GitHubRepoObject, RepoListAdapter.RepoListViewHolder>(diffUtil) {

    private var favoriteItems: List<LocalGitHubRepoObject>? = null

    interface OnItemClickListener {
        fun itemClick(item: GitHubRepoObject, isFavorite: Boolean)
    }

    inner class RepoListViewHolder(val binding: LayoutRepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: GitHubRepoObject) {
            binding.setVariable(BR.item, obj)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutRepoListItemBinding.inflate(inflater, parent, false)
        return RepoListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListViewHolder, position: Int) {
        val repoObject = getItem(position)
        val isFavorite: Boolean = favoriteItems?.any { it.id == repoObject.id } == true
        holder.bind(repoObject)
        holder.binding.root.setOnClickListener {
            itemClickListener.itemClick(repoObject, isFavorite)
        }

        if (isFavorite)
            holder.binding.favIcon.visibility = View.VISIBLE
        else
            holder.binding.favIcon.visibility = View.GONE

    }
}

/**
 * Diff Util Interface
 */
val diffUtil = object : DiffUtil.ItemCallback<GitHubRepoObject>() {
    override fun areItemsTheSame(oldItem: GitHubRepoObject, newItem: GitHubRepoObject): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: GitHubRepoObject, newItem: GitHubRepoObject): Boolean {
        return oldItem == newItem
    }
}
