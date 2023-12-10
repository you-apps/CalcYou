package net.youapps.calcyou.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material.icons.filled.KeyboardReturn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.R
import net.youapps.calcyou.data.backSpace
import net.youapps.calcyou.data.insertText
import net.youapps.calcyou.ui.components.buttons.KeyboardPanel
import net.youapps.calcyou.ui.components.buttons.KeyboardSpecialKey

@Composable
fun CharacterInputScreen() {
    Column(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxSize()
    ) {
        var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = textFieldValue,
            onValueChange = { textFieldValue = it },
            placeholder = {
                Text(
                    text = "E = mc²",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.5f)
                )
            },
            textStyle = MaterialTheme.typography.displaySmall,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(16.dp)
        )

        Divider(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp)
                .size(70.dp, 4.dp)
                .clip(RoundedCornerShape(50))
        )
        val rowScrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .horizontalScroll(rowScrollState)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            KeyboardSpecialKey(
                keyboardKey = stringResource(R.string.space),
                onClick = { textFieldValue = textFieldValue.insertText(" ") }
            )
            KeyboardSpecialKey(
                keyboardKey = stringResource(R.string.carriage_return),
                icon = Icons.Default.KeyboardReturn,
                onClick = { textFieldValue = textFieldValue.insertText("\n") }
            )
            KeyboardSpecialKey(
                keyboardKey = stringResource(R.string.backspace),
                icon = Icons.Default.Backspace,
                onClick = {
                    textFieldValue = textFieldValue.backSpace()
                }
            )
        }
        KeyboardPanel(
            onKeyPress = {
                textFieldValue = textFieldValue.insertText(it)
            }, modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun KeypadScreenPreview() {
    CharacterInputScreen()
}
