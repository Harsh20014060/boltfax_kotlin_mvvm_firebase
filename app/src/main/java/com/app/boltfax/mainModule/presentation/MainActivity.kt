package com.app.boltfax.mainModule.presentation

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.app.boltfax.R
import com.app.boltfax.base.BaseActivity
import com.app.boltfax.databinding.ActivityMainBinding
import com.etebarian.meowbottomnavigation.MeowBottomNavigation

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostMain) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        navController.addOnDestinationChangedListener { _, destination, arguments ->
        }

        configBottomNavigation()
    }

    private fun configBottomNavigation() {
        rootView.bottomNavigation.apply {

            add(MeowBottomNavigation.Model(1, R.drawable.home))
            add(MeowBottomNavigation.Model(2, R.drawable.category))
            add( MeowBottomNavigation.Model(3, R.drawable.cart))
            add( MeowBottomNavigation.Model(4, R.drawable.menu))
           show(1,true)
        }



        rootView.bottomNavigation.setOnClickMenuListener { model->
            when (model.id) {
                1 -> {
                    navController.navigate(R.id.navHomeFragment)
                }


                2 -> {
                    navController.navigate(R.id.navCategoryFragment)
                }

                3 -> {
                    navController.navigate(R.id.navCartFragment)
                }

                4 -> {
                    navController.navigate(R.id.navSettingFragment)
                }

                else -> {
                    navController.navigate(R.id.navHomeFragment)
                }
            }

        }
    }
}