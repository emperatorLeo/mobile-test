package com.example.mobile_test.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mobile_test.presentation.navigation.AppRoutes.HOME_SCREEN
import com.example.mobile_test.presentation.screen.HomeScreen
import com.example.mobile_test.presentation.screen.QRScreen
import com.example.mobile_test.presentation.screen.ScanScreen
import com.example.mobile_test.presentation.viewmodel.MainViewModel

@Composable
fun AppNavigation(
    viewModel: MainViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {

        composable(HOME_SCREEN) {
            HomeScreen(navController)
        }

        composable(AppRoutes.QR_SCREEN) {
            LaunchedEffect(Unit) {
                viewModel.getSeeds()
            }
            QRScreen(viewModel.uiState.collectAsState(), navController)
        }

        composable(AppRoutes.SCAN_SCREEN) {
            ScanScreen(navController)
        }

    }
}