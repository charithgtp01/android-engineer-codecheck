/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
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
import jp.co.yumemi.android.code_check.utils.LocalHelper
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
        initView()
        viewModelObservers()
    }

    /**
     * Initialize the UI components and setup navigation.
     */
    private fun initView() {
        DataBindingUtil.setContentView<ActivityMainBinding?>(this, R.layout.activity_main).apply {
            binding = this
            setupNavController()
            setSupportActionBar(toolbar)
            ViewModelProvider(this@MainActivity)[MainActivityViewModel::class.java].apply {
                sharedViewModel = this
                setFragmentName(LocalHelper.getString(this@MainActivity, R.string.menu_home))
            }

            btnBack.setOnClickListener {
                onBackPressed()
            }
        }

    }


    /**
     * Set up the navigation controller for the bottom navigation menu.
     */
    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        navHostFragment.navController.let { navController ->
            binding.bottomNavigationMenu.apply {
                bottomNavView = this
                this@MainActivity.menu = this.menu
                updateMenuValues(this@MainActivity, menu)
                setupWithNavController(navController)
            }
        }
    }

    /**
     * Observers for LiveData changes in the shared view model.
     */
    private fun viewModelObservers() {
        sharedViewModel.apply {
            /**
             * Observes changes in the sharedViewModel's fragment LiveData and updates the UI elements
             * in the MainActivity accordingly.
             *
             * @param fragment The LiveData that represents the current fragment.
             */

            fragment.observe(this@MainActivity) {
                // Set the title text based on the observed fragment
                when (it) {
                    HOME_FRAGMENT -> {
                        binding.btnBack.isVisible = false
                        bottomNavView.isVisible = true
                        setEmptyDataImage(true)
                        setFragmentName(
                            LocalHelper.getString(
                                this@MainActivity,
                                R.string.menu_home
                            )
                        )
                    }

                    ACCOUNT_DETAILS_FRAGMENT -> {
                        binding.btnBack.isVisible = true
                        bottomNavView.isVisible = false
                        setFragmentName(
                            LocalHelper.getString(
                                this@MainActivity,
                                R.string.details
                            )
                        )

                    }

                    FAVOURITE_FRAGMENT -> {
                        binding.btnBack.isVisible = false
                        bottomNavView.isVisible = true
                        setEmptyDataImage(true)
                        setFragmentName(
                            LocalHelper.getString(
                                this@MainActivity,
                                R.string.menu_favourites
                            )
                        )

                    }

                    SETTINGS_FRAGMENT -> {
                        expandedStates.clear()
                        binding.btnBack.isVisible = false
                        bottomNavView.isVisible = true
                        setEmptyDataImage(false)
                        setFragmentName(
                            LocalHelper.getString(
                                this@MainActivity,
                                R.string.menu_settings
                            )
                        )
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
            isSearchResultsEmpty.observe(this@MainActivity) { isSearchResultsEmpty ->
                isSearchResultsEmpty?.let { isEmpty ->
                    binding.emptyImageView.isVisible = isEmpty
                    if (isEmpty) {
                        binding.emptyImageView.setImageResource(
                            when (fragment.value) {
                                // In the Home Fragment, show an account search image
                                HOME_FRAGMENT -> ImageResources.getImageResources(
                                    ImageResources.GIT_ACCOUNT_SEARCH_IMAGE_CODE
                                )
                                // For other Fragments, show a no data image according to the language
                                else -> ImageResources.getImageResources(
                                    ImageResources.NO_DATA_IMAGE_CODE
                                )
                            }
                        )
                    }
                } ?: run {
                    // If the LiveData value is null, show the ImageView with a search account image
                    binding.emptyImageView.isVisible = true
                    binding.emptyImageView.setImageResource(R.mipmap.search_account)
                }
            }

            /**
             * Observe changes in a LiveData and update the bottom menu of the MainActivity accordingly.
             *
             * @param this@MainActivity The current MainActivity instance where this code is executed.
             * @param menu The bottom menu that needs to be updated.
             */
            updateBottomMenuStatus.observe(this@MainActivity) {
                updateMenuValues(this@MainActivity, menu)
            }

            /**
             * Observe changes in a LiveData and update the action bar title of the MainActivity accordingly.
             * Set live data value when the navigate through fragments
             */
            fragmentName.observe(this@MainActivity) {
                binding.title.text = it
            }
        }
    }
}
