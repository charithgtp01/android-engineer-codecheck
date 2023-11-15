/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.fragments.webprofileview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.databinding.FragmentWebProfileViewBinding
import jp.co.yumemi.android.code_check.ui.activities.MainActivityViewModel

/**
 * WebProfileViewFragment responsible for displaying a web profile view.
 *
 * This Fragment is used to show a web page specified by the provided HTML URL.
 * It assumes the presence of a WebView in the associated layout with the ID 'webView'.
 *
 * @property args Arguments received through Safe Args for navigation
 * @property binding View binding for this Fragment
 * @property htmlUrl HTML URL to be displayed in the WebView
 * @property sharedViewModel Shared ViewModel with the main activity
 */
class WebProfileViewFragment : Fragment() {
    private val args: WebProfileViewFragmentArgs by navArgs()
    private lateinit var binding: FragmentWebProfileViewBinding
    private var htmlUrl: String? = null
    private lateinit var sharedViewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Safe Args: Retrieve the HTML URL from the navigation arguments
        htmlUrl = args.htmlUrl
        FragmentWebProfileViewBinding.inflate(inflater, container, false).apply {
            binding = this
            lifecycleOwner = this@WebProfileViewFragment
            // Configure the WebView
            webView.run {
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true

                // Load the Git Hub User Profile HTML URL in the WebView
                htmlUrl?.let { loadUrl(it) }
            }
        }

        // Initialize the shared ViewModel with the main activity
        sharedViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        sharedViewModel.setFragment(StringConstants.WEB_PROFILE_VIEW_FRAGMENT)

        return binding.root
    }
}
