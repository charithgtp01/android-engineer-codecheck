/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.constants.ImageResources
import jp.co.yumemi.android.code_check.constants.StringConstants.ACCOUNT_DETAILS_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.FAVOURITE_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.HOME_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.SETTINGS_FRAGMENT
import jp.co.yumemi.android.code_check.databinding.ActivityMainBinding
import jp.co.yumemi.android.code_check.utils.UIUtils.Companion.updateMenuValues


/**
 * The main activity of the app, responsible for managing the UI and navigation.
 * This activity hosts various fragments and handles user interactions.
 *
 * This activity serves as the entry point to the app and handles the display of various
 * fragments and navigation through the bottom navigation menu.
 *
 * @property sharedViewModel The shared view model for communicating data and state between fragments.
 * @property binding The data binding object that allows for easy interaction with the layout XML.
 * @property bottomNavView The bottom navigation view for navigating between app sections.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the app and set up the UI
        setDataBinding()
        viewModelObservers()
        initView()
    }

    /**
     * Initialize the UI components and setup navigation.
     */
    private fun initView() {
        setupNavController()
        setSupportActionBar(binding.toolbar)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    /**
     * Set up data binding for the activity and initialize the shared view model.
     */
    private fun setDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }


    /**
     * Set up the navigation controller for the bottom navigation menu.
     */
    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView = findViewById(R.id.bottom_navigation_menu)
        menu = bottomNavView.menu
        updateMenuValues(this@MainActivity, menu)
        bottomNavView.setupWithNavController(navController)
    }

    /**
     * Observers for LiveData changes in the shared view model.
     */
    private fun viewModelObservers() {

        /**
         * Observes changes in the sharedViewModel's fragment LiveData and updates the UI elements
         * in the MainActivity accordingly.
         *
         * @param fragment The LiveData that represents the current fragment.
         */
        sharedViewModel.fragment.observe(this@MainActivity) {
            // Set the title text based on the observed fragment
            binding.title.text = it
            when (it) {
                HOME_FRAGMENT -> {
                    binding.btnBack.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                    sharedViewModel.setEmptyDataImage(true)
                }

                ACCOUNT_DETAILS_FRAGMENT -> {
                    binding.btnBack.visibility = View.VISIBLE
                    bottomNavView.visibility = View.GONE
                }

                FAVOURITE_FRAGMENT -> {
                    binding.btnBack.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                    sharedViewModel.setEmptyDataImage(true)
                }

                SETTINGS_FRAGMENT -> {
                    sharedViewModel.expandedStates.clear()
                    binding.btnBack.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                    sharedViewModel.setEmptyDataImage(false)
                }
            }
        }

        /**
         * Observes the [sharedViewModel.isSearchResultsEmpty] LiveData to handle changes in the
         * search results' emptiness.
         *
         * This method observes the LiveData and updates the visibility and image resource of
         * an [ImageView] based on whether the search results are empty or not.
         *
         * @param isSearchResultsEmpty The LiveData indicating whether the search results are empty.
         *   - If `null`, the [ImageView] is shown with a default search account image.
         *   - If `true`, the [ImageView] is shown with an image specific to the fragment,
         *     or a default no data image for other fragments.
         *   - If `false`, the [ImageView] is hidden.
         */
        sharedViewModel.isSearchResultsEmpty.observe(
            this
        ) { isSearchResultsEmpty ->
            if (isSearchResultsEmpty == null) {
                // If the LiveData value is null, show the ImageView with a search account image
                binding.emptyImageView.visibility = View.VISIBLE
                binding.emptyImageView.setImageResource(R.mipmap.search_account)
            } else {
                if (isSearchResultsEmpty) {
                    // If the search results are empty, show the ImageView with an appropriate image
                    binding.emptyImageView.visibility = View.VISIBLE
                    if (sharedViewModel.fragment.value == HOME_FRAGMENT) {
                        // In the Home Fragment, show an account search image
                        binding.emptyImageView.setImageResource(
                            ImageResources.getImageResources(
                                ImageResources.GIT_ACCOUNT_SEARCH_IMAGE_CODE
                            )
                        )
                    } else {
                        // For other Fragments, show a no data image according to the language
                        binding.emptyImageView.setImageResource(
                            ImageResources.getImageResources(
                                ImageResources.NO_DATA_IMAGE_CODE
                            )
                        )
                    }
                } else {
                    // If the search results are not empty, hide the ImageView
                    binding.emptyImageView.visibility = View.GONE
                }
            }
        }

        /**
         * This code snippet is used to observe changes in a LiveData and update the bottom menu of
         * the MainActivity accordingly.
         *
         * @param this@MainActivity The current MainActivity instance where this code is executed.
         * @param menu The bottom menu that needs to be updated.
         */
        sharedViewModel.updateBottomMenuStatus.observe(this) {
            updateMenuValues(this@MainActivity, menu)
        }
    }
}
