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
    private lateinit var bottomNavView:BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        binding.btnFav.setOnClickListener {
            // Handle the fav button click event
            sharedViewModel.addToFavourites()
        }
        sharedViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        sharedViewModel.updateBottomMenuStatus.observe(this) {
            updateMenuValues(this@MainActivity, menu)
        }

        setupNavController()
        viewModelObservers()

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
        /* According to the response show alert dialog(Error or Success) */
        sharedViewModel.localDBResponse.observe(this@MainActivity) {
            if (it != null) {
                if (it.success) {
                    showAlertDialogWithoutAction(
                        this@MainActivity,
                        DialogConstants.SUCCESS.value,
                        it.message)
                } else {
                    showAlertDialogWithoutAction(
                        this@MainActivity,
                        DialogConstants.FAIL.value, it.message
                    )
                }

            }
        }

        sharedViewModel.isHomeFragment.observe(this@MainActivity){
            if(it == true){
                binding.btnFav.visibility=View.GONE
                bottomNavView.visibility=View.VISIBLE
            }else{
                binding.btnFav.visibility=View.VISIBLE
                bottomNavView.visibility=View.GONE
            }
        }
    }


}
