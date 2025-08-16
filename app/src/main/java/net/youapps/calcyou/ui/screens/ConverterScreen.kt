package net.youapps.calcyou.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.R
import net.youapps.calcyou.data.converters.ConverterUnit
import net.youapps.calcyou.data.converters.MassConverter
import net.youapps.calcyou.data.converters.UnitConverter
import net.youapps.calcyou.data.evaluator.MathUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T> ConverterScreen(
    converter: UnitConverter<T>,
    @StringRes converterName: Int,
    keyboardType: KeyboardType,
    crossinline stringToConverterArg: (String) -> T?,
    converterArgToString: (T) -> String
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedUnit by remember { mutableStateOf(converter.units.first()) }
        var converted: List<Pair<ConverterUnit<T>, T>> by remember { mutableStateOf(listOf()) }
        var textFieldValue by remember { mutableStateOf("") }

        LaunchedEffect(textFieldValue, selectedUnit) {
            converted = stringToConverterArg(textFieldValue)
                ?.let { value -> converter.convertAll(value as T, selectedUnit) }.orEmpty()
        }
        Text(
            text = stringResource(id = converterName),
            style = MaterialTheme.typography.headlineLarge
        )
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                modifier = Modifier.weight(1f),
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                singleLine = true,
                label = { Text(stringResource(R.string.value)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    imeAction = ImeAction.None
                ),
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenuBox(
                modifier = Modifier.weight(1f),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor(),
                    readOnly = true,
                    value = stringResource(id = selectedUnit.name),
                    onValueChange = {},
                    label = {
                        Text(
                            stringResource(id = R.string.unit)
                        )
                    },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    shape = RoundedCornerShape(8.dp),
                    colors = ExposedDropdownMenuDefaults.textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    converter.units.forEach { unit ->
                        DropdownMenuItem(
                            text = { Text(stringResource(id = unit.name)) },
                            onClick = {
                                selectedUnit = unit
                                expanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }
        if (converted.isNotEmpty()) {
            val scroll = rememberScrollState()
            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scroll)
            ) {
                for ((unit, value) in converted) {
                    val formattedValue = converterArgToString(value)

                    ListItem(modifier = Modifier.fillMaxWidth(), headlineContent = {
                        Text(
                            text = stringResource(id = unit.name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }, supportingContent = {
                        Text(
                            text = formattedValue,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Light,

                            )
                    }, trailingContent = {
                        val clipboardManager = LocalClipboardManager.current
                        IconButton(onClick = {
                            clipboardManager.setText(AnnotatedString(formattedValue))
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.ContentCopy,
                                contentDescription = stringResource(
                                    R.string.copy_text
                                )
                            )
                        }
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ConverterScreen(
        converter = MassConverter(),
        R.string.app_name,
        KeyboardType.Number,
        converterArgToString = { double -> MathUtil.doubleToString(double) },
        stringToConverterArg = { str -> str.toDoubleOrNull() })
}