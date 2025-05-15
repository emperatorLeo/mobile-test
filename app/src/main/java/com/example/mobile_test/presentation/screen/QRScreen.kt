package com.example.mobile_test.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mobile_test.presentation.theme.Purple40
import com.example.mobile_test.presentation.viewmodel.MainViewModel
import qrgenerator.QRCodeImage

@Composable
fun QRScreen(viewModel: MainViewModel) {
    val context  = LocalContext.current
    Box(modifier = Modifier.fillMaxSize().background(Purple40)) {
        QRCodeImage(
            url = "https://www.dailypaws.com/living-with-pets/pet-compatibility/cutest-puppies",
            contentDescription = "go to puppies",
            modifier = Modifier.align(Alignment.Center),
            onFailure = {
                Toast.makeText(context, "there is something wrong: $it", Toast.LENGTH_SHORT).show()
            }
        )
    }
}