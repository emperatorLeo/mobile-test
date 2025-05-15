package com.example.mobile_test.presentation.screen

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import com.example.mobile_test.presentation.theme.Purple40
import com.example.mobile_test.presentation.theme.Purple80
import kotlinx.coroutines.launch
import qrscanner.QrScanner

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(navController: NavController) {
    val message = remember { mutableStateOf("") }
    val flashlightOn by remember { mutableStateOf(false) }
    var openImagePicker by remember { mutableStateOf(value = false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val showMessage = remember { mutableStateOf(false) }

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
            title = { Text("Scan Screen") },
            modifier = Modifier.align(Alignment.TopCenter)
        )
        QrScanner(
            modifier = Modifier
                .clipToBounds()
                .clip(shape = RoundedCornerShape(size = 14.dp))
                .size(300.dp)
                .align(Alignment.Center),
            flashlightOn = flashlightOn,
            onCompletion = {
                val intent = Intent(Intent.ACTION_VIEW, it.toUri())
                context.startActivity(intent, null)
            },
            onFailure = {
                coroutineScope.launch {
                    if (it.isEmpty()) {
                        message.value = "Invalid qr code"
                    } else {
                        message.value = it
                    }
                    showMessage.value = true
                    navController.popBackStack()
                }
            },
            openImagePicker = openImagePicker,
            imagePickerHandler = {
                openImagePicker = it
            }
        )
        if (showMessage.value) {
            Toast.makeText(context, "Scan something", Toast.LENGTH_SHORT).show()
        }
    }
}