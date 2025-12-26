package com.arnot.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arnot.app.screens.ScreenOne
import com.arnot.app.screens.ScreenThree
import com.arnot.app.screens.ScreenTwo
import com.arnot.app.ui.theme.ArnotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArnotTheme {
                ArnotApp()
            }
        }
    }
}

@Composable
fun ArnotApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            ScreenOne(
                onNavigate = { navController.navigate("ar_view") }
            )
        }

        composable("ar_view") {
            ScreenTwo(
                onBack = { navController.popBackStack() },
                onNavigateToGallery = { navController.navigate("gallery") }
            )
        }

        composable("gallery") {
            ScreenThree(
                onBack = { navController.popBackStack() }
            )
        }
    }
}