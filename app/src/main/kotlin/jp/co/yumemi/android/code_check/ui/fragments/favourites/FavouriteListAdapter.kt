package jp.co.yumemi.android.code_check.ui.fragments.favourites

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.databinding.LayoutRepoListItemBinding
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import javax.inject.Inject
import jp.co.yumemi.android.code_check.databinding.LayoutFavListItemBinding
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.BR

/**
 * Favorites Repo List Adapter
 */
class FavouriteListAdapter @Inject constructor(
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<FavouriteListAdapter.FavouriteListViewHolder>() {

    private val items: MutableList<LocalGitHubRepoObject> = mutableListOf()
    private var expandedStates: MutableMap<Long, Boolean> =
        mutableMapOf() // Map to store the expanded state

    /**
     * On Item Click Listener
     */
    interface OnItemClickListener {
        fun itemClick(item: LocalGitHubRepoObject, isExpanded: Boolean)
        fun deleteIconClick(item: LocalGitHubRepoObject)
    }

    inner class FavouriteListViewHolder(val binding: LayoutFavListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: LocalGitHubRepoObject) {
            val isExpanded = expandedStates[obj.id] == true

            binding.setVariable(BR.item, obj)
            binding.setVariable(BR.isExpanded, isExpanded)
            binding.executePendingBindings()

            if (isExpanded) {
                binding.nameLayout.tvContent.ellipsize = null
                binding.nameLayout.tvContent.maxLines = Int.MAX_VALUE
            } else {
                binding.nameLayout.tvContent.ellipsize = TextUtils.TruncateAt.END
                binding.nameLayout.tvContent.setLines(1)
            }

            // Set the visibility of your expanded content based on the state
            binding.expandedContent.visibility = if (isExpanded) View.VISIBLE else View.GONE

            //Normal View down arrow. Expanded view up arrow
            binding.forwardArrowIcon.rotation = if (isExpanded) 270F else 90F
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutFavListItemBinding.inflate(inflater, parent, false)
        return FavouriteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteListViewHolder, position: Int) {
        val repoObject = items[position]
        holder.bind(repoObject)
        holder.binding.root.setOnClickListener {
            // Toggle the expanded state when an item is clicked
            val isExpanded = expandedStates[repoObject.id] ?: false
            expandedStates[repoObject.id] = !isExpanded

            // Notify the adapter to refresh the view
            notifyDataSetChanged()

            itemClickListener.itemClick(repoObject, isExpanded)
        }

        holder.binding.deleteBtn.setOnClickListener {
            itemClickListener.deleteIconClick(repoObject)
        }
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newList: List<LocalGitHubRepoObject>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateStatus(status: MutableMap<Long, Boolean>) {
        expandedStates = status
    }
}
