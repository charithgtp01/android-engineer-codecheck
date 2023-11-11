/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import jp.co.yumemi.android.code_check.utils.LocalHelper
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.databinding.FragmentSettingsBinding
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager.Companion.updateSelectedLanguage

/**
 * SettingsFragment responsible for handling the Settings page.
 *
 * This fragment allows users to configure app settings such as language preferences.
 *
 * @property binding View Binding instance for the fragment's layout
 * @property viewModel View model for handling settings logic
 * @property sharedViewModel Shared view model with the MainActivity for updating layout changes
 *
 */
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var viewModel: SettingsViewModel

    //Main Activity view model
    private lateinit var sharedViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        FragmentSettingsBinding.inflate(inflater, container, false).apply {
            binding = this
            ViewModelProvider(requireActivity())[SettingsViewModel::class.java].apply {
                viewModel = this
                vm = this
            }
            lifecycleOwner = this@SettingsFragment
        }

        //This Shared view model is using to update Main Activity layout changes from this fragment
        ViewModelProvider(requireActivity())[MainActivityViewModel::class.java].apply {
            sharedViewModel = this
            setFragment(StringConstants.SETTINGS_FRAGMENT)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get selected language form preference and set to live data
       SharedPreferencesManager.getSelectedLanguage().apply {
           viewModel.setSelectedLanguage(
               this
           )
       }

        // When clicking on the languages layout, change the live selected language live data value
        // The updated value should be saved in the preference
        viewModel.apply {
            selectedLanguage.observe(viewLifecycleOwner) {
                // Update the selected value in the preference
                updateSelectedLanguage(it)
                setSelectedLanguageLabel(
                    LocalHelper.getString(
                        requireContext(),
                        R.string.select_app_language
                    )
                )
                //Update Main Activity bottom menu labels
                sharedViewModel.setUpdateBottomMenuStatus(true)
            }
        }

    }
}
