package net.youapps.calcyou.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.R
import net.youapps.calcyou.data.converters.ConverterUnit
import net.youapps.calcyou.data.converters.MassConverter
import net.youapps.calcyou.data.converters.UnitConverter

@Composable
fun ConverterScreen(converter: UnitConverter) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedUnit by remember { mutableStateOf(converter.units.first()) }
        var converted: List<Pair<ConverterUnit, Double>> by remember { mutableStateOf(listOf()) }
        var textFieldValue by remember { mutableStateOf("") }

        LaunchedEffect(textFieldValue, selectedUnit) {
            converted = textFieldValue.toDoubleOrNull()
                ?.let { it1 -> converter.convertAll(it1, selectedUnit) } ?: listOf()
        }
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = textFieldValue,
                onValueChange = {
                    textFieldValue = it
                },
                singleLine = true,
                modifier = Modifier.weight(1f),
                label = { Text(stringResource(R.string.value)) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.None
                ),
                shape = RoundedCornerShape(50)
            )

            OutlinedButton(onClick = { expanded = !expanded }) {
                Row {
                    Text(stringResource(selectedUnit.name))
                    Icon(Icons.Filled.ArrowDropDown, "")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }) {
                    converter.units.forEach { unit ->
                        DropdownMenuItem(onClick = {
                            selectedUnit = unit
                            expanded = false
                        }, text = {
                            Text(text = stringResource(unit.name))
                        })
                    }
                }
            }
        }
        if (converted.isNotEmpty()) {
            Column(
                Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Text(
                        stringResource(R.string.unit),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        stringResource(R.string.value),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        color = MaterialTheme.colorScheme.onPrimary

                    )
                }
                converted.forEach {
                    Row(Modifier.fillMaxWidth()) {
                        Text(
                            stringResource(id = it.first.name),
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            it.second.toString(),
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 4.dp, horizontal = 8.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ConverterScreen(converter = MassConverter())
}