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
import jp.co.yumemi.android.code_check.utils.DialogUtils
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog

/**
 * Details Page Fragment
 */

class RepoDetailsFragment : Fragment() {

    private val args: RepoDetailsFragmentArgs by navArgs()
    private var binding: FragmentRepoDetailsBinding? = null
    lateinit var viewModel: RepoDetailsViewModel
    private lateinit var gitHubRepo: GitHubRepoObject

    //Main Activity view model
    private lateinit var sharedViewModel: MainActivityViewModel

    private var localDBResponseObserver: Observer<LocalDBQueryResponse>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* Safe Args */
        gitHubRepo = args.item

        /*
         * Initiate Data Binding and View Model
         * Retrieve the Github Object from GitHubRepoListFragment
         */
        binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[RepoDetailsViewModel::class.java]
        viewModel.checkFavStatus(args.isFavourite)

        //This Shared view model is using to set selected git hub repo live data from this fragment
        sharedViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        sharedViewModel.setFragment(ACCOUNT_DETAILS_FRAGMENT)
        binding?.vm = viewModel
        binding?.lifecycleOwner = this

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setData()
        viewModelObservers()
    }

    private fun viewModelObservers() {

        /* According to the response show alert dialog(Error or Success) */
        localDBResponseObserver = Observer { response ->
            if (response.success) {
                DialogUtils.showAlertDialogWithoutAction(
                    requireActivity(),
                    DialogConstants.SUCCESS.value,
                    response.message
                )
            } else {
                DialogUtils.showAlertDialogWithoutAction(
                    requireActivity(),
                    DialogConstants.FAIL.value, response.message
                )
            }
        }

        /**
         * Success dialog will only be shown once when the localDBResponse LiveData is triggered,
         * even if rotate the device. This ensures that the dialog doesn't reappear on device rotation.
         */
        if (localDBResponseObserver != null) {
            // Observe the LiveData using a helper function observeOnce
            viewModel.localDBResponse.observeOnce(viewLifecycleOwner, localDBResponseObserver!!)
        }
    }

    private fun initView() {
        binding?.btnFav?.setOnClickListener {
            showConfirmAlertDialog(
                requireActivity(),
                LocalHelper.setLanguage(requireActivity(), R.string.add_fav_confirmation_message),
                object : ConfirmDialogButtonClickListener {
                    override fun onPositiveButtonClick() {
                        viewModel.addToFavourites()
                    }

                    override fun onNegativeButtonClick() {
                    }
                }
            )
        }

        //Handle back pressed event
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homeFragment)
                // Handle back button press for Home Fragment
                sharedViewModel.setFragment(HOME_FRAGMENT)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    /**
     * Pass selected Git Repo Object to view model
     * Set data to view
     */
    private fun setData() {
        viewModel.setGitRepoData(gitHubRepo)
        sharedViewModel.setFragment(ACCOUNT_DETAILS_FRAGMENT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        if (localDBResponseObserver != null) {
            // Remove the observer when the Fragment is destroyed
            viewModel.localDBResponse.removeObserver(localDBResponseObserver!!)
        }
    }

}
