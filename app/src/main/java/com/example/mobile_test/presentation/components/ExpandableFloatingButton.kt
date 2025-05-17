package com.example.mobile_test.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mobile_test.R
import com.example.mobile_test.presentation.navigation.AppRoutes
import com.example.mobile_test.presentation.theme.Purple40
import com.example.mobile_test.presentation.theme.Purple80

@Composable
fun ExpandableFloatingButton(navController: NavController) {
    var isExpanded by remember { mutableStateOf(false) }
    Column(horizontalAlignment = Alignment.End) {
        val transition = updateTransition(targetState = isExpanded, label = "transition")
        val rotation by transition.animateFloat(label = "rotation") {
            if (it) 300f else 0f
        }

        val fabItems =
            listOf(
                FabItems(
                    Icons.Filled.Call,
                    stringResource(R.string.scan_option),
                    route = AppRoutes.SCAN_SCREEN,
                ),
                FabItems(
                    Icons.Filled.Settings,
                    stringResource(R.string.generate_option),
                    route = AppRoutes.QR_SCREEN,
                ),
            )
        AnimatedVisibility(
            visible = isExpanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { it }) + shrinkVertically(),
        ) {
            LazyColumn(horizontalAlignment = Alignment.End) {
                items(fabItems) {
                    ItemUi(it) { route ->
                        navController.navigate(route)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { isExpanded = !isExpanded },
            modifier =
                Modifier
                    .rotate(rotation)
                    .padding(top = 10.dp),
            containerColor = Purple80,
            contentColor = Purple40,
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }
}

@Composable
private fun ItemUi(
    item: FabItems,
    onItemClicked: (String) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.clickable { onItemClicked(item.route) },
    ) {
        Text(item.label, modifier = Modifier.padding(end = 5.dp))
        SmallFloatingActionButton(
            onClick = { onItemClicked(item.route) },
            containerColor = Purple80,
            contentColor = Purple40,
        ) {
            Icon(item.icon, "")
        }
    }
}

data class FabItems(
    val icon: ImageVector,
    val label: String,
    val route: String,
)
