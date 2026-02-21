package com.karegraf.androidarchitecturestarterkit.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.karegraf.androidarchitecturestarterkit.presentation.AppViewModel
import com.karegraf.androidarchitecturestarterkit.presentation.Screen
import com.karegraf.androidarchitecturestarterkit.presentation.auth.login.views.LoginScreen
import com.karegraf.evrakapp.presentation.navigation.AppBottomBar
import com.karegraf.evrakapp.presentation.navigation.AppTopBar

@Composable
fun AppNavGraph(
    viewModel: AppViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val startDestination = viewModel.startDestination
    //eger user login ise cmpose tarafinda collectedAsState ile takip ediliyor
    val isLoggedIn =  viewModel.isLoggedIn.collectAsState()
    val hasToken = isLoggedIn.value

    val navBackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackEntry?.destination?.route ?: startDestination

    LaunchedEffect(Unit) {
        viewModel.navigateToLogin.collect { shouldNavigate ->
            if (shouldNavigate) {
                Log.d("AppNavGraph", "🔴 Session expired! Navigating to Login")
                navController.navigate(Screen.login.route) {
                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                }
            }
        }
    }

    Scaffold(
        topBar = { AppTopBar() },
        bottomBar = {
            if (hasToken) {
                AppBottomBar(
                    currentRoute = currentRoute
                ) { screen ->
                    //bottom bar item'ina tiklandiginda navigate et
                    // basit ve guvenli bir navigasyon icin popUpTo kullanarak back stack'i temizle
                    navController.navigate(screen.route) {
                        launchSingleTop = true // ayni route'a tekrar gitmeyi engelle

                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.login.route) {
                LoginScreen(navController = navController)
            }
        }
    }

}