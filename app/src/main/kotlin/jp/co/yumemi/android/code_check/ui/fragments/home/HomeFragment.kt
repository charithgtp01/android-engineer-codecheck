/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
import jp.co.yumemi.android.code_check.utils.NetworkUtils.Companion.isNetworkAvailable

/**
 * Home Fragment responsible for displaying a list of GitHub repositories and allowing users to search for repositories.
 * It handles user interactions, including searching, displaying details of a selected repository, and navigating to other fragments.
 *
 * This Fragment is part of the main navigation flow in the application.
 *
 * @constructor Creates a new instance of HomeFragment.
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedViewModel: MainActivityViewModel
    private lateinit var repoListAdapter: RepoListAdapter
    private var dialog: DialogFragment? = null
    private lateinit var dialogVisibleObserver: Observer<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        sharedViewModel.setFragment(StringConstants.HOME_FRAGMENT)

        binding.vm = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initiateAdapter()
        viewModelObservers()
    }

    /**
     * Initializes the user interface, sets up listeners, and performs language-specific configurations.
     */
    private fun initView() {

        viewModel.setSearchViewHint(
            LocalHelper.getString(
                requireContext(),
                R.string.searchInputText_hint
            )
        )

        binding.searchInputText.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    val enteredValue: String? = viewModel.searchViewText
                    //Empty value error Alert
                    when {
                        enteredValue?.isBlank() == true -> viewModel.setErrorMessage(
                            LocalHelper.getString(
                                requireActivity(), R.string.search_input_empty_error
                            )
                        )

                        else -> when {
                            isNetworkAvailable() -> viewModel.getGitHubRepoList(enteredValue)
                            else -> viewModel.setErrorMessage(
                                LocalHelper.getString(
                                    requireActivity(), R.string.no_internet
                                )
                            )
                        }
                    }
                    true
                }

                else -> false
            }
        }
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle back button press for Home Fragment
                showConfirmAlertDialog(requireActivity(), LocalHelper.getString(
                    requireActivity(), R.string.exit_confirmation_message
                ), object : ConfirmDialogButtonClickListener {
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
     * Initializes the RecyclerView adapter for displaying GitHub repositories.
     */
    private fun initiateAdapter() {/* Initiate Adapter */
        repoListAdapter = RepoListAdapter(object : RepoListAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubRepoObject, isFavorite: Boolean) {
                gotoRepositoryFragment(item, isFavorite)
            }
        })

        /* Set Adapter to Recycle View */
        binding.recyclerView.also { it2 ->
            it2.adapter = repoListAdapter
        }
    }

    /**
     * Observes LiveData updates from the ViewModel and updates the UI accordingly.
     */
    private fun viewModelObservers() {
        val progressDialogMessage = LocalHelper.getString(
            requireContext(), R.string.progress_dialog_message
        )/* Show error message in the custom error dialog */
        dialogVisibleObserver = Observer {
            when {
                dialog != null -> dialog?.dismiss()
            }
            dialog = showDialogWithoutActionInFragment(
                this@HomeFragment, it, DialogConstants.FAIL.value
            )
        }

        /* Live data observer to show/hide progress dialog */
        viewModel.isDialogVisible.observe(requireActivity()) {
            when {
                it -> {
                    when {
                        dialog != null -> dialog?.dismiss()
                    }/* Show dialog when calling the API */
                    dialog = showProgressDialogInFragment(
                        this@HomeFragment, progressDialogMessage
                    )

                }

                else -> dialog?.dismiss()
            }/* Dismiss dialog after updating the data list to recycle view */
        }

        /* Observer to catch list data
        * Update Recycle View Items using Diff Utils
        */
        viewModel.gitHubRepoList.observe(requireActivity()) {

            when {
                it.isEmpty() -> sharedViewModel.setEmptyDataImage(true)
                else -> sharedViewModel.setEmptyDataImage(false)
            }

            repoListAdapter.submitList(it)
        }

        dialogVisibleObserver.let {
            viewModel.errorMessage.observeOnce(
                viewLifecycleOwner,
                it
            )
        }
    }

    /**
     * Navigates to the RepositoryDetailsFragment when a GitHub repository item is clicked.
     *
     * @param gitHubRepo The selected GitHub repository object.
     * @param isFavorite Indicates whether the repository is marked as a favorite.
     */
    fun gotoRepositoryFragment(gitHubRepo: GitHubRepoObject, isFavorite: Boolean) {
        findNavController().navigate(
            HomeFragmentDirections.actionRepositoriesFragmentToRepositoryFragment(
                gitHubRepo, isFavorite
            )
        )
    }

    /**
     * This method is called when the view is destroyed. It removes the observer for error messages.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Remove the observer when the Fragment is destroyed
        viewModel.errorMessage.removeObserver(
            dialogVisibleObserver
        )
    }
}