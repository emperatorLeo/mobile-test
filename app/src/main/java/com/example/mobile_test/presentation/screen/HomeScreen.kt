package com.example.mobile_test.presentation.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.mobile_test.R
import com.example.mobile_test.presentation.components.ExpandableFloatingButton
import com.example.mobile_test.presentation.theme.Purple40
import com.example.mobile_test.presentation.theme.Purple80

@Suppress("ktlint:standard:function-naming")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple80,
                        titleContentColor = Purple40,
                    ),
                title = { Text(stringResource(R.string.home_screen_title)) },
            )
        },
        floatingActionButton = {
            ExpandableFloatingButton(navController)
        },
    ) { _ ->
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) { }
    }
}
