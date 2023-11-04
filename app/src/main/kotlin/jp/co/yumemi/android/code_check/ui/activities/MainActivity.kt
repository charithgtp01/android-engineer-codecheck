/*
 * Copyright © 2021 YUMEMI Inc. All rights reserved.
 */
package jp.co.yumemi.android.code_check.ui.activities

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.databinding.ActivityMainBinding
import jp.co.yumemi.android.code_check.databinding.FragmentSettingsBinding
import jp.co.yumemi.android.code_check.utils.UIUtils.Companion.updateMenuValues


/**
 * Main Activity Page
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var sharedViewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)

        binding.btnFav.setOnClickListener {
            // Handle the custom button click event
            Toast.makeText(this, "Custom Button Clicked", Toast.LENGTH_SHORT).show()
        }
        sharedViewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        sharedViewModel.updateBottomMenuStatus.observe(this) {
            updateMenuValues(this@MainActivity, menu)
        }

        setupNavController()
    }

    private lateinit var menu: Menu

    private fun setupNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation_menu)
        menu = bottomNavView.menu
        updateMenuValues(this@MainActivity, menu)
        bottomNavView.setupWithNavController(navController)
    }

}
