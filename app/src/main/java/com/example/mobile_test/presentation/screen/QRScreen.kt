package com.example.mobile_test.presentation.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.mobile_test.BuildConfig
import com.example.mobile_test.presentation.components.QRCodeShimmer
import com.example.mobile_test.presentation.states.UiState
import com.example.mobile_test.presentation.theme.Purple40
import com.example.mobile_test.presentation.theme.Purple80
import com.example.mobile_test.presentation.viewmodel.MainViewModel
import qrgenerator.QRCodeImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScreen(viewModel: MainViewModel, navigation: NavController) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getSeeds()
    }

    val state = viewModel.uiState.collectAsState()

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Purple80,
                titleContentColor = Purple40
            ),
            title = { Text("QR Screen") },
            modifier = Modifier.align(Alignment.TopCenter)
        )
        when (state.value) {
            is UiState.Success -> {
                QRCodeImage(
                    url = "${BuildConfig.BASE_URL}/${(state.value as UiState.Success).seed.seed}",
                    contentDescription = "go to item",
                    modifier = Modifier.align(Alignment.Center),
                    onFailure = {
                        Toast.makeText(context, "there is something wrong: $it", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
                Log.d("QR Success","Url: ${BuildConfig.BASE_URL}/${(state.value as UiState.Success).seed.seed}")
            }

            is UiState.Error -> {
                OnErrorAction(state.value as UiState.Error)
                navigation.popBackStack()
            }

            is UiState.Loading -> {
                QRCodeShimmer(Modifier.align(Alignment.Center))
            }

            is UiState.Idle -> {}
        }
    }
}

@Composable
fun OnErrorAction(state: UiState.Error){
    val context = LocalContext.current
    Toast.makeText(
        context,
        "there was something wrong: ${state.message}; please try again",
        Toast.LENGTH_SHORT
    ).show()
}