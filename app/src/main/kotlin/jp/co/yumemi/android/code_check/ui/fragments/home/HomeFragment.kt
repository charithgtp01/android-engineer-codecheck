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
import jp.co.yumemi.android.code_check.utils.LocalHelper
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
 * HomeFragment for displaying a list of GitHub repositories and handling user interactions.
 *
 * This fragment is responsible for initializing the user interface, setting up listeners,
 * and observing LiveData updates from the associated [HomeViewModel]. Allows the user to search for
 * repositories by entering a search term in a search view and displays a list of GitHub
 * repositories and . Additionally, it handles the navigation to the [RepositoryFragment]
 * when a repository item is clicked.
 *
 * @constructor Creates an instance of [HomeFragment].
 *
 * @property binding Binding for the fragment layout
 * @property viewModel ViewModel for the HomeFragment
 * @property sharedViewModel Shared ViewModel for communication with the MainActivity
 * @property repoListAdapter Adapter for the list of GitHub repositories
 * @property dialog Dialog fragment for showing error messages and progress
 * @property dialogVisibleObserver Observer for handling dialog visibility
 */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var sharedViewModel: MainActivityViewModel
    private lateinit var repoListAdapter: RepoListAdapter
    private var dialog: DialogFragment? = null
    private lateinit var dialogVisibleObserver: Observer<String?>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflating the layout using View Binding
        FragmentHomeBinding.inflate(inflater, container, false).apply {
            binding = this
            // Initializing HomeViewModel using ViewModelProvider
            ViewModelProvider(requireActivity())[HomeViewModel::class.java].apply {
                viewModel = this
                vm = this
            }
            // Setting the lifecycle owner for data binding
            lifecycleOwner = this@HomeFragment
        }
        // Initializing and setting up MainActivityViewModel
        ViewModelProvider(requireActivity())[MainActivityViewModel::class.java].apply {
            sharedViewModel = this
            setFragment(StringConstants.HOME_FRAGMENT)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObservers()
    }

    /**
     * Initializes the user interface, sets up listeners, and performs language-specific
     * configurations.
     */
    private fun initView() {
        // Setting the search view hint based on the localized string
        viewModel.apply {
            setSearchViewHint(
                LocalHelper.getString(
                    requireContext(),
                    R.string.searchInputText_hint
                )
            )
            // Setting up the searchInputText's OnEditorActionListener
            binding.apply {
                searchInputText.setOnEditorActionListener { _, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_SEARCH -> {
                            val enteredValue: String? = searchViewText
                            //Empty value error Alert
                            when {
                                enteredValue.isNullOrBlank() -> setErrorMessage(
                                    LocalHelper.getString(
                                        requireActivity(), R.string.search_input_empty_error
                                    )
                                )

                                else -> when {
                                    isNetworkAvailable() -> getGitHubRepoList(enteredValue)
                                    else -> setErrorMessage(
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

                // Initializing RepoListAdapter and setting it to RecyclerView
                RepoListAdapter(
                    object : RepoListAdapter.OnItemClickListener {
                        override fun itemClick(item: GitHubRepoObject, isFavorite: Boolean) {
                            navigateToRepositoryFragment(item, isFavorite)
                        }
                    }).apply {
                    repoListAdapter = this
                    /* Set Adapter to Recycle View */
                    recyclerView.also { it2 ->
                        it2.adapter = this
                    }
                }
            }
        }

        // Handle back button press for Home Fragment
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showConfirmAlertDialog(requireActivity(),
                    LocalHelper.getString(requireActivity(), R.string.exit_confirmation_message),
                    object : ConfirmDialogButtonClickListener {
                        override fun onPositiveButtonClick() {
                            requireActivity().finish()
                        }

                        override fun onNegativeButtonClick() {
                        }
                    }
                )
            }
        }.apply {
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, this)
        }
    }

    /**
     * Observes LiveData updates from the ViewModel and updates the UI accordingly.
     */
    private fun viewModelObservers() {
        val progressDialogMessage = LocalHelper.getString(
            requireContext(), R.string.progress_dialog_message
        )

        /* Show error message in the custom error dialog */
        dialogVisibleObserver = Observer {
            // Dismiss the dialog if it's not null
            dialog?.dismiss()

            // Show the dialog only if it's not null
            dialog =
                showDialogWithoutActionInFragment(
                    this@HomeFragment,
                    it,
                    DialogConstants.FAIL.value
                ).takeUnless { dialog -> dialog == null }

        }

        /* Live data observer to show/hide progress dialog */
        viewModel.isDialogVisible.observe(requireActivity()) { isVisible ->
            isVisible?.let { showDialog ->
                when {
                    // If the showDialog == true, show the dialog else dismiss the dialog
                    showDialog -> {
                        dialog?.dismiss()
                        dialog = showProgressDialogInFragment(
                            this@HomeFragment, progressDialogMessage
                        )
                    }

                    else -> dialog?.dismiss()
                }
            }
        }

        // Observer to catch list data
        // Update RecyclerView Items using DiffUtils
        viewModel.gitHubRepoList.observe(requireActivity()) { repoList ->
            repoList?.let {
                when {
                    it.isEmpty() -> sharedViewModel.setEmptyDataImage(true)
                    else -> sharedViewModel.setEmptyDataImage(false)
                }

                repoListAdapter.submitList(it)
            }
        }

        viewModel.allFavourites?.observe(requireActivity()){repoList ->
            repoList?.let {
                repoListAdapter.setFavourites(repoList)
            }
        }

        dialogVisibleObserver.let {
            viewModel.errorMessage.observeOnce(
                viewLifecycleOwner,
                it
            )
        }
    }

    /**
     * Navigates to the [RepositoryFragment] when a GitHub repository item is clicked.
     *
     * @param gitHubRepo The selected GitHub repository object.
     * @param isFavorite Indicates whether the repository is marked as a favorite.
     */
    fun navigateToRepositoryFragment(gitHubRepo: GitHubRepoObject, isFavorite: Boolean) {
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