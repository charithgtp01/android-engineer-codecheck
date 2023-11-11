/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.repodetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.LocalHelper
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.SingleLiveEvent.Companion.observeOnce
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.constants.StringConstants.ACCOUNT_DETAILS_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.HOME_FRAGMENT
import jp.co.yumemi.android.code_check.databinding.FragmentRepoDetailsBinding
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.models.LocalDBQueryResponse
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showAlertDialogWithoutAction
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog

/**
 * Fragment that displays details of a GitHub repository and allows users to mark it as a favorite.
 *
 * This fragment is responsible for showing detailed information about a selected GitHub repository,
 * allowing users to mark it as a favorite, and handling back navigation to the Home Fragment. It
 * communicates with the [RepoDetailsViewModel] to manage the repository data and favorite status.
 *
 * @property args Arguments received from the navigation component, including the GitHub repository item
 * @property binding View binding for this fragment's layout
 * @property viewModel View model for managing repository details and favorite status
 * @property gitHubRepo The selected GitHub repository to be displayed
 * @property isFavourite Flag indicating whether the repository is marked as a favorite
 * @property sharedViewModel View model shared with the main activity
 * @property localDBResponseObserver Observer for handling responses from local database operations
 */
class RepoDetailsFragment : Fragment() {
    private val args: RepoDetailsFragmentArgs by navArgs()
    private lateinit var binding: FragmentRepoDetailsBinding
    lateinit var viewModel: RepoDetailsViewModel
    private lateinit var gitHubRepo: GitHubRepoObject
    private var isFavourite: Boolean = false
    private lateinit var sharedViewModel: MainActivityViewModel
    private var localDBResponseObserver: Observer<LocalDBQueryResponse>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Safe Args: Retrieve the GitHub repository object
        gitHubRepo = args.item
        FragmentRepoDetailsBinding.inflate(inflater, container, false).apply {
            binding = this
            ViewModelProvider(requireActivity())[RepoDetailsViewModel::class.java].apply {
                viewModel = this
                vm = this

                // If savedInstanceState is null, set isFavourite from the arguments.
                // If savedInstanceState is not null, get isFavourite from the ViewModel.
                isFavourite =
                    when (savedInstanceState) {
                        null -> args.isFavourite
                        else -> favouriteStatus.value ?: false
                    }
            }
            lifecycleOwner = this@RepoDetailsFragment
        }

        // Initialize the shared ViewModel with the main activity
        sharedViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        viewModelObservers()
    }

    /**
     * Initialize ViewModel observers.
     */
    private fun viewModelObservers() {

        /* According to the response show alert dialog(Error or Success) */
        localDBResponseObserver = Observer { response ->
            when {
                response.success -> showAlertDialogWithoutAction(
                    requireActivity(),
                    DialogConstants.SUCCESS.value,
                    response.message
                )

                else -> showAlertDialogWithoutAction(
                    requireActivity(),
                    DialogConstants.FAIL.value, response.message
                )
            }
        }

        /**
         * Success dialog will only be shown once when the localDBResponse LiveData is triggered,
         * even if rotate the device. This ensures that the dialog doesn't reappear on device rotation.
         * Observe the LiveData using a helper function observeOnce
         */
        localDBResponseObserver?.let {
            viewModel.localDBResponse.observeOnce(viewLifecycleOwner, it)
        }
    }

    /**
     * Initialize the view components.
     */
    private fun initView() {
        viewModel.apply {
            binding.apply {
                btnFav.setOnClickListener {
                    val confirmationMessage = favouriteStatus.value?.let {
                        if (it) {
                            R.string.remove_fav_confirmation_message
                        } else {
                            R.string.add_fav_confirmation_message
                        }
                    } ?: R.string.add_fav_confirmation_message

                    showConfirmAlertDialog(
                        requireActivity(),
                        LocalHelper.getString(requireActivity(), confirmationMessage),
                        object : ConfirmDialogButtonClickListener {
                            override fun onPositiveButtonClick() {
                                when (favouriteStatus.value) {
                                    true -> deleteFavourite(gitHubRepo.id)
                                    else -> addToFavourites()
                                }
                            }

                            override fun onNegativeButtonClick() {
                            }
                        }
                    )
                }

            }

            setGitRepoData(gitHubRepo)
            checkFavStatus(isFavourite)
            sharedViewModel.setFragment(ACCOUNT_DETAILS_FRAGMENT)
        }

        //Handle back pressed event
         object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
                // Handle back button press for Home Fragment
                sharedViewModel.setFragment(HOME_FRAGMENT)
            }
        }.apply {
             requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, this)
         }
    }

    /**
     * Called when the view previously created by [onCreateView] has been detached from the fragment.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        localDBResponseObserver?.let { observer ->
            // Remove the observer when the Fragment is destroyed
            viewModel.localDBResponse.removeObserver(observer)
        }
    }
}
