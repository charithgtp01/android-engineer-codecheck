/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.LocalHelper
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.SingleLiveEvent.Companion.observeOnce
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.databinding.FragmentHomeBinding
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showDialogWithoutActionInFragment
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showProgressDialogInFragment

/**
 * Home Page Fragment
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
//    private val TAG: String = HomeFragment::class.java.simpleName
    private var binding: FragmentHomeBinding? = null
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedViewModel: MainActivityViewModel
    private lateinit var repoListAdapter: RepoListAdapter
    private var dialog: DialogFragment? = null
    private var dialogVisibleObserver: Observer<String>? = null

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
        sharedViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        sharedViewModel.setFragment(StringConstants.HOME_FRAGMENT)

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
        if (binding != null) {
            binding!!.searchInputText.hint =
                LocalHelper.setLanguage(requireContext(), R.string.searchInputText_hint)
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle back button press for Home Fragment
                showConfirmAlertDialog(
                    requireActivity(),
                    LocalHelper.setLanguage(
                        requireActivity(),
                        R.string.exit_confirmation_message
                    ),
                    object : ConfirmDialogButtonClickListener {
                        override fun onPositiveButtonClick() {
                            requireActivity().finish()
                        }

                        override fun onNegativeButtonClick() {

                        }
                    })
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    /**
     * Recycle View data configuration
     */
    private fun initiateAdapter() {
        /* Initiate Adapter */
        repoListAdapter =
            RepoListAdapter(object : RepoListAdapter.OnItemClickListener {
                override fun itemClick(item: GitHubRepoObject, isFavorite: Boolean) {
                    gotoRepositoryFragment(item, isFavorite)
                }
            })

        /* Set Adapter to Recycle View */
        binding?.recyclerView.also { it2 ->
            it2?.adapter = repoListAdapter
        }
    }

    /**
     * Live Data Updates
     */
    private fun viewModelObservers() {
        val progressDialogMessage = LocalHelper.setLanguage(
            requireContext(),
            R.string.progress_dialog_message
        )
        /* Show error message in the custom error dialog */
        dialogVisibleObserver = Observer { it ->
            if (dialog != null)
                dialog?.dismiss()
            dialog = showDialogWithoutActionInFragment(
                this@HomeFragment,
                it,
                DialogConstants.FAIL.value
            )
        }

        /* Live data observer to show/hide progress dialog */
        viewModel.isDialogVisible.observe(requireActivity()) {
            if (it) {
                if (dialog != null)
                    dialog?.dismiss()
                /* Show dialog when calling the API */
                dialog =
                    showProgressDialogInFragment(
                        this@HomeFragment,
                        progressDialogMessage
                    )

            } else {
                /* Dismiss dialog after updating the data list to recycle view */
                dialog?.dismiss()
            }
        }

        /* Observer to catch list data
        * Update Recycle View Items using Diff Utils
        */
        viewModel.gitHubRepoList.observe(requireActivity()) {
            if(it.isEmpty())
                sharedViewModel.setEmptyDataImage(true)
            else
                sharedViewModel.setEmptyDataImage(false)

            repoListAdapter.submitList(it)
        }

        if (dialogVisibleObserver != null) {
            // Observe the LiveData using a helper function observeOnce
            viewModel.errorMessage.observeOnce(viewLifecycleOwner, dialogVisibleObserver!!)
        }
    }

    /**
     * Navigate to Next Fragment Using Navigation Controller
     * Pass selected Git Hub Repo Object using Safe Args
     */
    fun gotoRepositoryFragment(gitHubRepo: GitHubRepoObject, isFavorite: Boolean) {
        findNavController().navigate(
            HomeFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(
                gitHubRepo, isFavorite
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null

        if (dialogVisibleObserver != null) {
            // Remove the observer when the Fragment is destroyed
            viewModel.errorMessage.removeObserver(dialogVisibleObserver!!)
        }
    }
}