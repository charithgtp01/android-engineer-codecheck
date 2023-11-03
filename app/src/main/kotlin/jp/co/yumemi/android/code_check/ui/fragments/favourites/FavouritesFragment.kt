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
import jp.co.yumemi.android.code_check.databinding.FragmentFavouritesBinding
import jp.co.yumemi.android.code_check.models.LocalGitHubRepoObject
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel

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

        binding?.vm = viewModel
        binding?.lifecycleOwner = this

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateAdapter()
        viewModelObservers()
    }

    /**
     * Recycle View data configuration
     */
    private fun initiateAdapter() {
        /* Initiate Adapter */
        favouriteListAdapter =
            FavouriteListAdapter(object : FavouriteListAdapter.OnItemClickListener {
                override fun itemClick(item: LocalGitHubRepoObject) {

                }
            })

        /* Set Adapter to Recycle View */
        binding?.recyclerView.also { it2 ->
            it2?.adapter = favouriteListAdapter
        }
    }

    /**
     * Live Data Updates
     */
    private fun viewModelObservers() {

        /* Observer to catch list data
        * Update Recycle View Items using Diff Utils
        */
        viewModel.allFavourites.observe(requireActivity()) {
            if(binding!=null){
                if(it.isEmpty())
                    binding!!.emptyImageView.visibility=View.VISIBLE
                else
                    binding!!.emptyImageView.visibility=View.GONE
            }

            favouriteListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
