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
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.FragmentSettingsBinding

/**
 * Settings Page Fragment
 */

class SettingsFragment : Fragment() {

    private var binding: FragmentSettingsBinding? = null
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[SettingsViewModel::class.java]
        binding?.vm = viewModel
        binding?.lifecycleOwner = this

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setBgRes(R.drawable.selected_layout_bg)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
