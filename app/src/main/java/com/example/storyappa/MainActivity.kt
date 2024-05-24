package com.example.storyappa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.storyappa.databinding.ActivityMainBinding
import com.example.storyappa.utils.ConstVal
import com.example.storyappa.utils.ext.gone
import com.example.storyappa.utils.ext.show

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomNav()
    }

    private fun setupBottomNav() {
        val navHostBottomBar =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navControllerBottomBar = navHostBottomBar.navController

        with(binding) {
            mainBottomNavigation.setupWithNavController(navControllerBottomBar)
            navControllerBottomBar.addOnDestinationChangedListener { _, destination, _ ->
                if (ConstVal.navBarDestination.contains(destination.id)) {
                    mainBottomNavigation.show()
                } else {
                    mainBottomNavigation.gone()
                }
            }
        }
    }
}