package net.youapps.calcyou.ui.components.buttons

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun KeyRow(
    title: String,
    items: Array<String>,
    onKeyPress: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        val scrollState = rememberScrollState()
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items.forEach { item ->
                KeyboardKey(keyboardKey = item, onClick = { onKeyPress(item) })
            }
        }
    }
}
