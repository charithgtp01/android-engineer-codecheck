package jp.co.yumemi.android.code_check.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutRepoListItemBinding
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import javax.inject.Inject
import jp.co.yumemi.android.code_check.BR

/**
 * Git Repo List Adapter
 */
class RepoListAdapter @Inject constructor(private val itemClickListener: OnItemClickListener) :
    ListAdapter<GitHubRepoObject, RepoListAdapter.RepoListViewHolder>(diffUtil) {


    /**
     * On Item Click Listener
     */
    interface OnItemClickListener {
        fun itemClick(item: GitHubRepoObject)
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
        holder.bind(repoObject)
        holder.binding.root.setOnClickListener {
            itemClickListener.itemClick(repoObject)
        }
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