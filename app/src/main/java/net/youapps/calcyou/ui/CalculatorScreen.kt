package net.youapps.calcyou.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen() {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text("CalcYou") })
    }) { paddingValues ->
        CalculatorView(modifier = Modifier.padding(paddingValues))
    }
}