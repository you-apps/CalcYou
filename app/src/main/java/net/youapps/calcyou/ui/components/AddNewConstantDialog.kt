package net.youapps.calcyou.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.youapps.calcyou.R
import net.youapps.calcyou.data.graphing.Constant

@Composable
fun AddNewConstantDialog(
    modifier: Modifier = Modifier,
    onConfirm: (constant: Constant) -> Unit,
    onCancel: () -> Unit,
    initialConstant: Constant,
) {
    Dialog(onCancel) {
        Box(
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = stringResource(R.string.add_constant),
                    style = MaterialTheme.typography.titleLarge
                )
                var text by remember { mutableStateOf(initialConstant.value.toString()) }
                var isError by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = text,
                    onValueChange = {
                        text = it
                        isError = strToDouble(it) == null
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    prefix = {
                        Text(
                            text = "${initialConstant.identifier} = ", style = TextStyle(
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Medium,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontStyle = FontStyle.Italic
                            )
                        )
                    },
                    textStyle = TextStyle(
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Medium,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontStyle = FontStyle.Italic
                    )
                )
                Row(Modifier.align(Alignment.End)) {
                    TextButton(onClick = onCancel) {
                        Text(text = stringResource(id = android.R.string.cancel))
                    }
                    TextButton(onClick = {
                        val value = strToDouble(text) ?: return@TextButton
                        onConfirm(initialConstant.copy(value = value))
                    }, enabled = !isError) {
                        Text(text = stringResource(id = android.R.string.ok))
                    }
                }
            }
        }
    }
}

private fun strToDouble(string: String): Double? {
    return string.replace(",", ".").toDoubleOrNull()
}