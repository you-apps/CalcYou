package net.youapps.calcyou.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.youapps.calcyou.ui.components.CalculatorDisplay
import net.youapps.calcyou.ui.components.Keypad
import net.youapps.calcyou.viewmodels.CalculatorViewModel

@Composable
fun CalculatorScreen(
    modifier: Modifier = Modifier,
    calculatorViewModel: CalculatorViewModel = viewModel(factory = CalculatorViewModel.Factory)
) {
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

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    CalculatorScreen()
}