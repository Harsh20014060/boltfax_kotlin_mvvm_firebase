package com.app.boltfax.authModule.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import com.app.boltfax.R
import com.app.boltfax.base.BaseActivity
import com.app.boltfax.databinding.ActivityAuthenticationBinding
import com.app.boltfax.mainModule.presentation.MainActivity

class AuthenticationActivity :
    BaseActivity<ActivityAuthenticationBinding>(ActivityAuthenticationBinding::inflate) {

    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.navHostAuthentication) as NavHostFragment
    }

    private val navController by lazy {
        navHostFragment.navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startActivity(Intent(this, MainActivity::class.java))

        navController.addOnDestinationChangedListener { _, destination, arguments ->
        }
    }
}