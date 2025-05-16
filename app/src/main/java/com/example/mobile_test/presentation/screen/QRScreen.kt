package com.example.mobile_test.presentation.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_test.BuildConfig
import com.example.mobile_test.R
import com.example.mobile_test.presentation.components.QRCodeShimmer
import com.example.mobile_test.presentation.states.UiState
import com.example.mobile_test.presentation.theme.Purple40
import com.example.mobile_test.presentation.theme.Purple80
import kotlinx.coroutines.delay
import qrgenerator.QRCodeImage
import java.time.Instant
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QRScreen(uiState: State<UiState>, navigation: NavController, retry: () -> Unit) {
    val context = LocalContext.current
    var time by remember { mutableIntStateOf(0) }
    var isRunning by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Purple80,
                titleContentColor = Purple40
            ),
            title = { Text(stringResource(R.string.qr_screen_title)) },
            modifier = Modifier.align(Alignment.TopCenter)
        )
        when (uiState.value) {
            is UiState.Success -> {
                isRunning = true
                val seed = (uiState.value as UiState.Success).seed
                Column(Modifier.align(Alignment.Center)) {
                    QRCodeImage(
                        url = "${BuildConfig.BASE_URL}/${seed.seed}",
                        contentDescription = "",
                        onFailure = {
                            Toast.makeText(
                                context,
                                "there is something wrong: $it",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    )

                    val timeFormatter = DateTimeFormatter.ISO_DATE_TIME
                    val accessor = timeFormatter.parse(seed.expiration)
                    val exp = Instant.from(accessor)
                    val now = Instant.now()
                    time = (exp.epochSecond - now.epochSecond).toInt()

                    Text(
                        stringResource(R.string.time,time),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    LaunchedEffect(isRunning) {
                        while (isRunning) {
                            delay(1000)
                            if (time == 0) {
                                isRunning = false
                                retry.invoke()
                            }
                            time--
                        }
                    }
                }
            }

            is UiState.Error -> {
                OnErrorAction(uiState.value as UiState.Error)
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
fun OnErrorAction(state: UiState.Error) {
    val context = LocalContext.current
    Toast.makeText(
        context,
        "there was something wrong: ${state.message}; please try again",
        Toast.LENGTH_SHORT
    ).show()
}