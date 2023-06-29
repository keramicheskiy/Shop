package com.example.shop111

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.shop111.databinding.MainPageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainPage : BaseActivity() {
    private lateinit var binding: MainPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navView : BottomNavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragmentActivityDashBoard) as NavHostFragment
        val navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_dashboard,
                R.id.navigation_products,
                R.id.navigation_orders,
                R.id.navigation_sold_products

            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


    }
}