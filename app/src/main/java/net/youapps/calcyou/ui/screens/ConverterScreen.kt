package net.youapps.calcyou.ui.screens

import android.content.Context
import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import net.youapps.calcyou.R
import net.youapps.calcyou.data.Either
import net.youapps.calcyou.data.converters.ConverterUnit
import net.youapps.calcyou.data.converters.MassConverter
import net.youapps.calcyou.data.converters.UnitConverter
import net.youapps.calcyou.data.evaluator.MathUtil
import net.youapps.calcyou.ui.components.buttons.CalculatorButton
import net.youapps.calcyou.viewmodels.ConverterViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T> ConverterScreen(
    converter: UnitConverter<T>,
    @StringRes converterName: Int,
    keyboardType: KeyboardType,
    crossinline stringToConverterArg: (String) -> T?,
    crossinline converterArgToString: (T) -> String,
    converterViewModel: ConverterViewModel = viewModel(factory = ConverterViewModel.Factory),
    crossinline formatNumberFromString: (String)->String,
    crossinline formatOctNumberFromString: (String)->String,
    crossinline formatHexNumberFromString: (String)->String,
    crossinline formatBinNumberFromString: (String)->String,
    decimalSep: String
){
    val orientation = LocalConfiguration.current.orientation
    if (orientation == ORIENTATION_LANDSCAPE) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(24.dp))
            ) {
                ConverterKeypad(
                    converterViewModel = converterViewModel,
                    decimalSep = decimalSep,
                    keyboardType = keyboardType
                )
            }
            Box(modifier = Modifier.weight(1f)) {
                ConverterDisplaySide(
                    converter,
                    converterName,
                    keyboardType,
                    stringToConverterArg,
                    converterArgToString,
                    converterViewModel,
                    formatNumberFromString,
                    formatOctNumberFromString,
                    formatHexNumberFromString,
                    formatBinNumberFromString
                )
            }
        }
    }else{
        Column(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Box(modifier = Modifier.weight(1f)) {
                ConverterDisplaySide(
                    converter,
                    converterName,
                    keyboardType,
                    stringToConverterArg,
                    converterArgToString,
                    converterViewModel,
                    formatNumberFromString,
                    formatOctNumberFromString,
                    formatHexNumberFromString,
                    formatBinNumberFromString
                )
            }
            Box(modifier = Modifier.clip(RoundedCornerShape(24.dp))) {
                ConverterKeypad(
                    converterViewModel = converterViewModel,
                    decimalSep = decimalSep,
                    keyboardType = keyboardType
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T> ConverterDisplaySide(
    converter: UnitConverter<T>,
    @StringRes converterName: Int,
    keyboardType: KeyboardType,
    crossinline stringToConverterArg: (String) -> T?,
    crossinline converterArgToString: (T) -> String,
    converterViewModel: ConverterViewModel = viewModel(factory = ConverterViewModel.Factory),
    crossinline formatNumberFromString: (String)->String,
    crossinline formatOctNumberFromString: (String)->String,
    crossinline formatHexNumberFromString: (String)->String,
    crossinline formatBinNumberFromString: (String)->String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var expanded by remember { mutableStateOf(false) }
        var selectedUnit by remember { mutableStateOf(converter.units.first()) }
        var converted: List<Pair<ConverterUnit<T>, T>> by remember { mutableStateOf(listOf()) }

        LaunchedEffect(converterViewModel.textFieldValue, selectedUnit) {
            scope.launch {
                converted = stringToConverterArg(converterViewModel.textFieldValue)
                    ?.let { value -> converter.convertAll(value as T, selectedUnit) }.orEmpty()
            }
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
            val selectedUnitName = unitName(context, selectedUnit.name)
            CompositionLocalProvider(LocalTextInputService provides null) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = if (keyboardType != KeyboardType.Text) {
                        formatNumberFromString(converterViewModel.textFieldValue)
                    } else if (selectedUnitName == stringResource(R.string.octal)) {
                        formatOctNumberFromString(converterViewModel.textFieldValue)
                    } else if (selectedUnitName == stringResource(R.string.hexadecimal)) {
                        formatHexNumberFromString(converterViewModel.textFieldValue)
                    } else if (selectedUnitName == stringResource(R.string.binary)) {
                        formatBinNumberFromString(converterViewModel.textFieldValue)
                    } else {
                        formatNumberFromString(converterViewModel.textFieldValue)
                    },
                    onValueChange = {
                        converterViewModel.textFieldValue = it
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
            }
            ExposedDropdownMenuBox(
                modifier = Modifier.weight(1f),
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor(),
                    readOnly = true,
                    value = unitName(context, selectedUnit.name),
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
                var dropdownWidth by remember {
                    mutableStateOf(0.dp)
                }

                ExposedDropdownMenu (
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.onGloballyPositioned {
                        dropdownWidth = it.size.width.dp
                    }
                ) {
                    val itemHeight = 45.dp
                    val configuration = LocalConfiguration.current
                    val dropdownHeight = remember(converter.units.size) {
                        val height = itemHeight * converter.units.size
                        val maxHeight = configuration.screenHeightDp.dp * 0.7f
                        minOf(height, maxHeight)
                    }
                    LazyColumn(
                        modifier = Modifier
                            .width(dropdownWidth)
                            .height(dropdownHeight)
                    ) {
                        items(converter.units) { unit ->
                            DropdownMenuItem(
                                text = { Text(unitName(context, unit.name)) },
                                onClick = {
                                    selectedUnit = unit
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                                modifier = Modifier.height(itemHeight)
                            )
                        }
                    }
                }
            }
        }
        if (converted.isNotEmpty()) {
            LazyColumn(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(converted) { (unit, value) ->
                    val formattedValue = converterArgToString(value)

                    ListItem(modifier = Modifier.fillMaxWidth(), headlineContent = {
                        Text(
                            text = unitName(context, unit.name),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }, supportingContent = {
                        val currentUnitName = unitName(context, unit.name)
                        Text(
                            text = if(keyboardType != KeyboardType.Text) {
                                formatNumberFromString(formattedValue)
                            }else if(currentUnitName == stringResource(R.string.octal)){
                                formatOctNumberFromString(formattedValue)
                            }else if(currentUnitName == stringResource(R.string.hexadecimal)){
                                formatHexNumberFromString(formattedValue)
                            }else if(currentUnitName == stringResource(R.string.binary)){
                                formatBinNumberFromString(formattedValue)
                            }else{
                                formatNumberFromString(formattedValue)
                            },
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

@Composable
fun ConverterKeypad(
    converterViewModel: ConverterViewModel,
    decimalSep: String,
    keyboardType: KeyboardType
) {
    val buttonSpacing = 8.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "6",
                onClick = {
                    converterViewModel.textFieldValue += "6"
                }
            )
            CalculatorButton(
                text = "7",
                onClick = {
                    converterViewModel.textFieldValue += "7"
                }
            )
            CalculatorButton(
                text = "8",
                onClick = {
                    converterViewModel.textFieldValue += "8"
                }
            )
            CalculatorButton(
                text = "9",
                onClick = {
                    converterViewModel.textFieldValue += "9"
                }
            )
            if(keyboardType == KeyboardType.Text){
                CalculatorButton(
                    text = "e",
                    onClick = {
                        converterViewModel.textFieldValue += "e"
                    }
                )
                CalculatorButton(
                    text = "f",
                    onClick = {
                        converterViewModel.textFieldValue += "f"
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "2",
                onClick = {
                    converterViewModel.textFieldValue += "2"
                }
            )
            CalculatorButton(
                text = "3",
                onClick = {
                    converterViewModel.textFieldValue += "3"
                }
            )
            CalculatorButton(
                text = "4",
                onClick = {
                    converterViewModel.textFieldValue += "4"
                }
            )
            CalculatorButton(
                text = "5",
                onClick = {
                    converterViewModel.textFieldValue += "5"
                }
            )
            if(keyboardType == KeyboardType.Text){
                CalculatorButton(
                    text = "c",
                    onClick = {
                        converterViewModel.textFieldValue += "c"
                    }
                )
                CalculatorButton(
                    text = "d",
                    onClick = {
                        converterViewModel.textFieldValue += "d"
                    }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "0",
                onClick = {
                    converterViewModel.textFieldValue += "0"
                }
            )
            CalculatorButton(
                text = "1",
                onClick = {
                    converterViewModel.textFieldValue += "1"
                }
            )
            CalculatorButton(
                text = remember { decimalSep },
                onClick = {
                    converterViewModel.textFieldValue += "."
                }
            )
            CalculatorButton(
                iconRes = R.drawable.delete,
                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                textColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    if(converterViewModel.textFieldValue.isNotEmpty()){
                        converterViewModel.textFieldValue = converterViewModel.textFieldValue.dropLast(1)
                    }
                },
                onLongClick = {
                    converterViewModel.textFieldValue = ""
                }
            )
            if(keyboardType == KeyboardType.Text){
                CalculatorButton(
                    text = "a",
                    onClick = {
                        converterViewModel.textFieldValue += "a"
                    }
                )
                CalculatorButton(
                    text = "b",
                    onClick = {
                        converterViewModel.textFieldValue += "b"
                    }
                )
            }
        }
    }
}

fun unitName(context: Context, name: Either<Int, String>): String {
    return when (name) {
        is Either.Left -> context.getString(name.value)
        is Either.Right -> name.value
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
        stringToConverterArg = { str -> str.toDoubleOrNull() },
        decimalSep = ",",
        formatNumberFromString = {""},
        converterViewModel = ConverterViewModel(LocalContext.current),
        formatOctNumberFromString = {""},
        formatHexNumberFromString = {""},
        formatBinNumberFromString = {""}
    )
}