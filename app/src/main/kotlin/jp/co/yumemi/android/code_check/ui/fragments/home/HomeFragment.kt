/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.databinding.FragmentHomeBinding
import jp.co.yumemi.android.code_check.item
import jp.co.yumemi.android.code_check.models.RepoObject

/**
 * Home Page Fragment
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var repoListAdapter: RepoListAdapter
    private var dialog: DialogFragment? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*
         * Initiate Data Binding and View Model
        */
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        binding?.vm = viewModel
        binding?.lifecycleOwner = this
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateAdapter()
    }

    /**
     * Recycle View data configuration
     */
    private fun initiateAdapter() {
        /* Initiate Adapter */
        repoListAdapter =
            RepoListAdapter(object : RepoListAdapter.OnItemClickListener {
                override fun itemClick(item: RepoObject) {
                    val gson = Gson()
//                    val prefMap = HashMap<String, String>()
//                    prefMap[Constants.OBJECT_STRING] = gson.toJson(item)
//                    navigateToAnotherActivityWithExtras(
//                        requireActivity(),
//                        UserDetailsActivity::class.java,
//                        prefMap
//                    )
                }
            })

        /* Set Adapter to Recycle View */
        binding?.recyclerView.also { it2 ->
            it2?.adapter = repoListAdapter
        }
    }

    fun gotoRepositoryFragment(item: item) {
        val _action =
            HomeFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(item = item)
        findNavController().navigate(_action)
    }
}