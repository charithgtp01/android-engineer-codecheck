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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.LocalHelper
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.constants.StringConstants.ACCOUNT_DETAILS_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.HOME_FRAGMENT
import jp.co.yumemi.android.code_check.databinding.FragmentRepoDetailsBinding
import jp.co.yumemi.android.code_check.interfaces.ConfirmDialogButtonClickListener
import jp.co.yumemi.android.code_check.models.GitHubRepoObject
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel
import jp.co.yumemi.android.code_check.utils.DialogUtils
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showConfirmAlertDialog
import jp.co.yumemi.android.code_check.utils.UIUtils.Companion.changeUiSize

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
        viewModel.localDBResponse.observe(requireActivity()) {
            if (it != null) {
                if (it.success) {
                    DialogUtils.showAlertDialogWithoutAction(
                        requireActivity(),
                        DialogConstants.SUCCESS.value,
                        it.message
                    )
                } else {
                    DialogUtils.showAlertDialogWithoutAction(
                        requireActivity(),
                        DialogConstants.FAIL.value, it.message
                    )
                }

            }
        }
    }

    private fun initView() {
        binding?.btnFav?.setOnClickListener {
            showConfirmAlertDialog(
                requireActivity(),
                LocalHelper.setLanguage(requireActivity(),R.string.add_fav_confirmation_message),
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
    }
}
