/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jp.co.yumemi.android.code_check.LocalHelper
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.StringConstants.FAVOURITE_FRAGMENT
import jp.co.yumemi.android.code_check.databinding.FragmentFavouritesBinding
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog
import jp.co.yumemi.android.code_check.utils.UIUtils

/**
 * Settings Page Fragment
 */

class FavouritesFragment : Fragment() {

    private var binding: FragmentFavouritesBinding? = null
    private lateinit var viewModel: FavouritesViewModel

    //Main Activity view model
    private lateinit var sharedViewModel: MainActivityViewModel

    private lateinit var favouriteListAdapter: FavouriteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[FavouritesViewModel::class.java]

        //This Shared view model is using to update Main Activity layout changes from this fragment
        sharedViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        sharedViewModel.setFragment(FAVOURITE_FRAGMENT)
        binding?.vm = viewModel
        binding?.lifecycleOwner = this

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initiateAdapter()
        viewModelObservers()
    }

    private fun initView() {
        //Set Empty image size
        if (binding != null)
            UIUtils.changeUiSize(requireContext(), binding!!.emptyImageView, 2, 3)
    }

    /**
     * Recycle View data configuration
     */
    private fun initiateAdapter() {
        /* Initiate Adapter */
        favouriteListAdapter =
            FavouriteListAdapter(object : FavouriteListAdapter.OnItemClickListener {
                override fun itemClick(item: LocalGitHubRepoObject, isExpanded: Boolean) {
                    sharedViewModel.expandedStates[item.id] = !isExpanded
                }

                override fun deleteIconClick(item: LocalGitHubRepoObject) {
                    showConfirmAlertDialog(
                        requireActivity(),
                        LocalHelper.setLanguage(
                            requireActivity(),
                            R.string.remove_fav_confirmation_message
                        ),
                        object : ConfirmDialogButtonClickListener {
                            override fun onPositiveButtonClick() {
                                viewModel.deleteFavourite(item.id)
                            }

                            override fun onNegativeButtonClick() {
                            }
                        }
                    )
                }
            })

        /* Set Adapter to Recycle View */
        binding?.recyclerView.also { it2 ->
            it2?.adapter = favouriteListAdapter
        }

        favouriteListAdapter.updateStatus(sharedViewModel.expandedStates)
    }

    /**
     * Live Data Updates
     */
    private fun viewModelObservers() {

        /* Observer to catch list data
        * Update Recycle View Items using Diff Utils
        */
        viewModel.allFavourites.observe(requireActivity()) {
            if (binding != null) {
                if (it.isEmpty())
                    binding!!.emptyImageView.visibility = View.VISIBLE
                else
                    binding!!.emptyImageView.visibility = View.GONE
            }

            favouriteListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
