package jp.co.yumemi.android.code_check.ui.fragments.favourites

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import jp.co.yumemi.android.code_check.BR
import jp.co.yumemi.android.code_check.databinding.LayoutFavListItemBinding
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import javax.inject.Inject

/**
 * RecyclerView adapter for displaying a list of favorite GitHub repositories.
 *
 * This adapter handles the display and interaction of a list of favorite GitHub repositories in a RecyclerView.
 * It supports expanding and collapsing items to show additional information and provides callbacks for item clicks
 * and delete icon clicks.
 *
 * @property itemClickListener The listener to handle item clicks and delete icon clicks.
 */
class FavouriteListAdapter @Inject constructor(
    private val itemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<FavouriteListAdapter.FavouriteListViewHolder>() {

    private val items = mutableListOf<LocalGitHubRepoObject>()
    private var expandedStates =
        mutableMapOf<Long, Boolean>() // Map to store the expanded state

    /**
     * Interface definition for a callback to be invoked when an item in the list is clicked.
     */
    interface OnItemClickListener {
        /**
         * Called when an item in the list is clicked.
         *
         * @param item The clicked GitHub repository item.
         * @param isExpanded Indicates whether the item is expanded.
         */
        fun itemClick(item: LocalGitHubRepoObject, isExpanded: Boolean)

        /**
         * Called when the delete icon of an item in the list is clicked.
         *
         * @param item The GitHub repository item associated with the delete icon.
         */
        fun deleteIconClick(item: LocalGitHubRepoObject)
    }

    inner class FavouriteListViewHolder(val binding: LayoutFavListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(obj: LocalGitHubRepoObject) {
            val isExpanded = expandedStates[obj.id] ?: false

            binding.apply {
                setVariable(BR.item, obj)
                setVariable(BR.isExpanded, isExpanded)
                executePendingBindings()

                nameLayout.apply {
                    when {
                        isExpanded -> {
                            //Show all content and view height will adjust according to the content
                            tvContent.ellipsize = null
                            tvContent.maxLines = Int.MAX_VALUE
                        }
                        else -> {
                            //Show only one line with ... at the end
                            //This will display only if the content is too long
                            tvContent.ellipsize = TextUtils.TruncateAt.END
                            tvContent.setLines(1)
                        }
                    }
                }

                // Set the visibility of your expanded content based on the state
                expandedContent.isVisible = isExpanded

                // Normal View down arrow. Expanded view up arrow
                forwardArrowIcon.rotation = when {
                    isExpanded -> 270F
                    else -> 90F
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = LayoutFavListItemBinding.inflate(inflater, parent, false)
        return FavouriteListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteListViewHolder, position: Int) {
        val repoObject = items[position]

        holder.apply {
            bind(repoObject)
            binding.apply {
                root.setOnClickListener {
                    // Toggle the expanded state when an item is clicked
                    val isExpanded = expandedStates[repoObject.id] ?: false
                    expandedStates[repoObject.id] = !isExpanded

                    // Notify the adapter to refresh the view
                    notifyDataSetChanged()

                    itemClickListener.itemClick(repoObject, isExpanded)
                }

                deleteBtn.setOnClickListener {
                    itemClickListener.deleteIconClick(repoObject)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size

    /**
     * Submits a new list of favorite GitHub repositories to be displayed.
     *
     * @param newList The new list of favorite GitHub repositories.
     */
    fun submitList(newList: List<LocalGitHubRepoObject>) {
        items.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    /**
     * Updates the expanded states of the items in the list.
     *
     * @param status The map containing the updated expanded states.
     */
    fun updateStatus(status: MutableMap<Long, Boolean>) {
        expandedStates = status
    }
}
