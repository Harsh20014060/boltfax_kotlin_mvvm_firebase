package com.example.boltfax_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.boltfax_compose.presentation.auth.ScreenLogin
import com.example.boltfax_compose.ui.BoltFax_ComposeTheme
import com.example.boltfax_compose.utils.NavUtils

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            BoltFax_ComposeTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()

                ) { innerPadding ->
                    val navController = rememberNavController()
                    Column(
                        modifier = Modifier
                            .background(brush = AppBrushes.BackgroundGradient)
                            .fillMaxSize()
                            .padding(innerPadding)


                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = NavUtils.NAV_SCREEN_LOGIN,
                            modifier = Modifier.fillMaxSize()
                        ) {

                            composable(NavUtils.NAV_SCREEN_LOGIN) { ScreenLogin(navController) }

                        }
                    }


                }
            }
        }
    }


}