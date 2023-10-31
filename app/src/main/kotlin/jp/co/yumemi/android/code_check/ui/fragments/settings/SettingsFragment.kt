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
import jp.co.yumemi.android.code_check.LocalHelper
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSettingsBinding
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager
import jp.co.yumemi.android.code_check.utils.SharedPreferencesManager.Companion.updateSelectedLanguage

/**
 * Settings Page Fragment
 */

class SettingsFragment : Fragment() {

    private var binding: FragmentSettingsBinding? = null
    private lateinit var viewModel: SettingsViewModel

    //Main Activity view model
    private lateinit var sharedViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]

        //This Shared view model is using to update Main Activity layout changes from this fragment
        sharedViewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]

        binding?.vm = viewModel
        binding?.lifecycleOwner = this

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Get selected language form preference and set to live data
        val language = SharedPreferencesManager.getSelectedLanguage()

        viewModel.setSelectedLanguage(
            language
        )

        //When click on the languages layout changing the live selected language live data value
        //Updated value should save in the preference
        viewModel.selectedLanguage.observe(requireActivity()) {
            //Update selected value in the preference
            updateSelectedLanguage(it)
            binding?.textView?.text =
                LocalHelper.setLanguage(this.context, R.string.select_app_language)

            //Update Main Activity bottom menu lables
            sharedViewModel.setUpdateBottomMenuStatus(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
