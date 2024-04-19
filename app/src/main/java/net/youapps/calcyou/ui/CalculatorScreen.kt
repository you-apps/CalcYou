package net.youapps.calcyou.ui

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.youapps.calcyou.ui.components.CalculatorDisplay
import net.youapps.calcyou.ui.components.CenterKeypadHorizontal
import net.youapps.calcyou.ui.components.Keypad
import net.youapps.calcyou.ui.components.SideKeypadHorizontal
import net.youapps.calcyou.viewmodels.CalculatorViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel = viewModel(factory = CalculatorViewModel.Factory)
) {
    val orientation = LocalConfiguration.current.orientation
    if (orientation == ORIENTATION_LANDSCAPE) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier.weight(1f)) {
                SideKeypadHorizontal(
                    onEvent = calculatorViewModel::onEvent,
                    square = false,
                    textStyle = MaterialTheme.typography.headlineSmall
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                CalculatorDisplay(
                    calculatorViewModel,
                    primaryTextStyle = MaterialTheme.typography.headlineMedium,
                    secondaryTextStyle = MaterialTheme.typography.headlineSmall
                )
                CenterKeypadHorizontal(
                    calculatorViewModel::onEvent,
                    textStyle = MaterialTheme.typography.headlineSmall,
                    iconSize = 32.dp
                )
            }
        }
    } else {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .then(modifier),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorDisplay(calculatorViewModel)
            Keypad(onEvent = calculatorViewModel::onEvent)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    CalculatorScreen()
}