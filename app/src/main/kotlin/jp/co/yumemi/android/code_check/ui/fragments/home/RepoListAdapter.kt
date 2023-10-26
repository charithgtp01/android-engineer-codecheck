package jp.co.yumemi.android.code_check.ui.fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutRepoListItemBinding
import jp.co.yumemi.android.code_check.models.RepoObject
import javax.inject.Inject
import androidx.databinding.library.baseAdapters.BR

/**
 * Git Repo List Adapter
 */
class RepoListAdapter @Inject constructor(private val itemClickListener: OnItemClickListener) :
    ListAdapter<RepoObject, RepoListAdapter.RepoListViewHolder>(diffUtil) {


    /**
     * On Item Click Listener
     */
    interface OnItemClickListener {
        fun itemClick(item: RepoObject)
    }

    inner class RepoListViewHolder(val binding: LayoutRepoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: RepoObject) {
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
val diffUtil = object : DiffUtil.ItemCallback<RepoObject>() {
    override fun areItemsTheSame(oldItem: RepoObject, newItem: RepoObject): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: RepoObject, newItem: RepoObject): Boolean {
        return oldItem == newItem
    }
}
