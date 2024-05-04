package net.youapps.calcyou.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.youapps.calcyou.R
import net.youapps.calcyou.viewmodels.GraphViewModel

@Composable
fun AddNewFunctionDialog(
    graphViewModel: GraphViewModel,
    initialExpression: String,
    initialColor: Color,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest) {
        DialogContent(
            onConfirm = { expression, color ->
                graphViewModel.addFunction(expression, color)
                onDismissRequest()
            },
            onCancel = onDismissRequest,
            checkExpression = graphViewModel::checkExpression,
            isError = graphViewModel.isError,
            errorMessage = graphViewModel.errorText,
            initialExpression = initialExpression,
            initialColor = initialColor
        )
    }
}

@Composable
private fun DialogContent(
    modifier: Modifier = Modifier,
    onConfirm: (expression: String, color: Color) -> Unit,
    onCancel: () -> Unit,
    checkExpression: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    initialExpression: String = "",
    initialColor: Color = rainbowColors.first(),
) {
    var color by remember { mutableStateOf(initialColor) }
    var showColorPicker by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
    ) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = stringResource(R.string.add_new_function),
                style = MaterialTheme.typography.titleLarge
            )
            var text by remember { mutableStateOf(initialExpression) }
            OutlinedTextField(
                value = text,
                onValueChange = {
                    text = it
                    checkExpression(it)
                },
                isError = isError,
                prefix = {
                    Text(
                        text = "f(x) = ", style = TextStyle(
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Medium,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontStyle = FontStyle.Italic
                        )
                    )
                },
                trailingIcon = {
                    Box(
                        Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(color)
                            .clickable {
                                showColorPicker = true
                            })
                },
                textStyle = TextStyle(
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Medium,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontStyle = FontStyle.Italic
                )
            )
            AnimatedVisibility(isError) {
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
            Row(Modifier.align(Alignment.End)) {
                TextButton(onClick = onCancel) {
                    Text(text = stringResource(id = android.R.string.cancel))
                }
                TextButton(onClick = {
                    onConfirm(text, color)
                }, enabled = !isError) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            }
        }
    }
    if (showColorPicker) {
        ColorSelectionDialog(initialColor = remember { color },
            onDismiss = { showColorPicker = false }) {
            color = it
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun DialogContentPreview() {
    DialogContent(
        onConfirm = { _, _ -> },
        onCancel = {},
        checkExpression = {},
        isError = true,
        errorMessage = "Invalid token at index 0"
    )
}