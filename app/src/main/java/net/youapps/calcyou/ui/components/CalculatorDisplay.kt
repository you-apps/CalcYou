package net.youapps.calcyou.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.viewmodels.CalculatorViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.CalculatorDisplay(
    calculatorViewModel: CalculatorViewModel,
    primaryTextStyle: TextStyle = MaterialTheme.typography.displayMedium,
    secondaryTextStyle: TextStyle = MaterialTheme.typography.displaySmall
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1.0f)
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp))
    ) {
        val listState = rememberLazyListState()

        LaunchedEffect(calculatorViewModel.history.toList()) {
            if (calculatorViewModel.history.isNotEmpty())
                listState.animateScrollToItem(calculatorViewModel.history.size - 1);
        }
        LazyColumn(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .weight(1f),
            state = listState,
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Bottom)
        ) {
            items(items = calculatorViewModel.history) { item ->
                Text(
                    text = item,
                    modifier = Modifier
                        .animateItemPlacement()
                        .clickable {
                            calculatorViewModel.setExpression(item)
                        },
                    textAlign = TextAlign.End,
                    style = secondaryTextStyle,
                    color = MaterialTheme.colorScheme.onSurface,
                )
            }
        }
        Row(
            Modifier
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(50.dp)),
            horizontalArrangement = Arrangement.End
        ) {
            CompositionLocalProvider(LocalTextInputService provides null) {
                BasicTextField(
                    value = calculatorViewModel.displayText,
                    onValueChange = {
                        calculatorViewModel.displayText = it
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    maxLines = 1,
                    textStyle = primaryTextStyle.plus(
                        TextStyle(
                            textAlign = TextAlign.Right,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                )
            }
        }
    }

}