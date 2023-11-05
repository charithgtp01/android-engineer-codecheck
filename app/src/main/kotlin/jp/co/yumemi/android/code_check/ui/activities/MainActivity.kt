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
import jp.co.yumemi.android.code_check.constants.DialogConstants
import jp.co.yumemi.android.code_check.constants.StringConstants
import jp.co.yumemi.android.code_check.constants.StringConstants.ACCOUNT_DETAILS_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.FAVOURITE_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.HOME_FRAGMENT
import jp.co.yumemi.android.code_check.constants.StringConstants.SETTINGS_FRAGMENT
import jp.co.yumemi.android.code_check.databinding.ActivityMainBinding
import jp.co.yumemi.android.code_check.interfaces.CustomAlertDialogListener
import jp.co.yumemi.android.code_check.utils.DialogUtils
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showAlertDialog
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showAlertDialogWithoutAction
import jp.co.yumemi.android.code_check.utils.DialogUtils.Companion.showDialogWithoutActionInFragment
import jp.co.yumemi.android.code_check.utils.UIUtils.Companion.updateMenuValues


/**
 * Main Activity Page
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // add the default theme here which we want
        // to display after the splash screen is shown

        setDataBinding()
        viewModelObservers()
        initView()
    }

    private fun initView() {
        setupNavController()
        setSupportActionBar(binding.toolbar)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private lateinit var menu: Menu

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        menu = bottomNavView.menu
        updateMenuValues(this@MainActivity, menu)
        bottomNavView.setupWithNavController(navController)
    }

    /**
     * Live Data Observer
     */
    private fun viewModelObservers() {

        //Can write fragment related changes here(UI changes, Device Rotation status, etc)
        //When moving to out from the Favourite Fragment
        //Need to remove saved expanded status by calling sharedViewModel.expandedStates.clear()
        //No need to do call sharedViewModel.expandedStates.clear() in Favourite Fragment.
        //It will keep expanded status and show expanded items by getting it from sharedView model
        sharedViewModel.fragment.observe(this@MainActivity) {
            binding.title.text= it
            when (it) {
                HOME_FRAGMENT -> {
                    binding.btnBack.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                }
                ACCOUNT_DETAILS_FRAGMENT -> {
                    binding.btnBack.visibility = View.VISIBLE
                    bottomNavView.visibility = View.GONE
                }
                FAVOURITE_FRAGMENT -> {
                    binding.btnBack.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                }
                SETTINGS_FRAGMENT -> {
                    sharedViewModel.expandedStates.clear()
                    binding.btnBack.visibility = View.GONE
                    bottomNavView.visibility = View.VISIBLE
                }
            }
        }

        sharedViewModel.updateBottomMenuStatus.observe(this) {
            updateMenuValues(this@MainActivity, menu)
        }
    }
}
