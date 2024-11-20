package net.youapps.calcyou.ui.screens.graphing

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import net.youapps.calcyou.R
import net.youapps.calcyou.data.graphing.Constant
import net.youapps.calcyou.data.graphing.Defaults
import net.youapps.calcyou.data.graphing.Function
import net.youapps.calcyou.ui.components.AddNewConstantDialog
import net.youapps.calcyou.ui.components.AddNewFunctionDialog
import net.youapps.calcyou.viewmodels.GraphViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphingScreen(graphViewModel: GraphViewModel = viewModel()) {
    var showAddFunctionDialog by remember { mutableStateOf(false) }
    var showAddConstantDialog by remember { mutableStateOf(false) }
    val bottomSheetState = rememberStandardBottomSheetState(
        skipHiddenState = true
    )
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )
    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                FunctionConstantsList(
                    modifier = Modifier.padding(top = 16.dp, start = 8.dp, end = 8.dp),
                    functions = graphViewModel.functions,
                    constants = graphViewModel.constants,
                    onClickFunction = {
                        graphViewModel.updateSelectedFunction(it)
                        showAddFunctionDialog = true
                    },
                    onClickRemoveFunction = {
                        graphViewModel.removeFunction(it)
                    },
                    onClickConstant = {
                        graphViewModel.selectedConstantIndex = it
                        showAddConstantDialog = true
                    },
                    onClickRemoveConstant = {
                        graphViewModel.constants.removeAt(it)
                    }
                )
                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                    AnimatedVisibility(graphViewModel.constants.size < Defaults.defaultConstantNameChars.size) {
                        OutlinedButton(onClick = {
                            showAddConstantDialog = true
                        }) {
                            Text(text = stringResource(R.string.add_constant))
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            graphViewModel.updateSelectedFunction(-1)
                            showAddFunctionDialog = true
                        }) {
                        Text(text = stringResource(R.string.add_new_function))
                    }
                }
            }
        }) {
        CanvasView(graphViewModel)
    }
    if (showAddFunctionDialog) {
        AddNewFunctionDialog(
            graphViewModel = graphViewModel,
            onDismissRequest = {
                graphViewModel.updateSelectedFunction(-1)
                showAddFunctionDialog = false
            },
            functionName = remember(graphViewModel.selectedFunctionIndex) { graphViewModel.functionName },
            initialColor =
            remember(graphViewModel.selectedFunctionIndex) { graphViewModel.functionColor },
            initialExpression =
            remember(graphViewModel.selectedFunctionIndex) { graphViewModel.expression },
        )
    }
    if (showAddConstantDialog) {
        AddNewConstantDialog(
            onConfirm = { constant ->
                if (graphViewModel.selectedConstantIndex != -1) {
                    graphViewModel.constants[graphViewModel.selectedConstantIndex] = constant
                } else {
                    graphViewModel.constants.add(constant)
                }

                graphViewModel.selectedConstantIndex = -1
                showAddConstantDialog = false
            },
            onCancel = {
                graphViewModel.selectedConstantIndex = -1
                showAddConstantDialog = false
            },
            initialConstant = graphViewModel.constants.getOrNull(graphViewModel.selectedConstantIndex)
                ?: Constant(
                    Defaults.defaultConstantNameChars.first { identifier ->
                        graphViewModel.constants.none { it.identifier == identifier }
                    },
                    0.0
                )
        )
    }
}

@Composable
fun FunctionConstantsList(
    modifier: Modifier = Modifier,
    functions: List<Function>,
    constants: List<Constant>,
    onClickFunction: (Int) -> Unit,
    onClickRemoveFunction: (Int) -> Unit,
    onClickConstant: (Int) -> Unit,
    onClickRemoveConstant: (Int) -> Unit
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            functions,
            key = { index, function -> "$index-${function.hashCode()}" }) { index, function ->
            FunctionRow(
                lhsName = "${function.name}(x)",
                text = function.expression,
                color = function.color,
                onClick = { onClickFunction(index) },
                onClickRemove = {
                    onClickRemoveFunction(index)
                }
            )
            Divider(Modifier.fillMaxWidth())
        }

        itemsIndexed(constants, key = { _, constant -> constant.hashCode() }) { index, constant ->
            FunctionRow(
                lhsName = constant.identifier.toString(),
                text = constant.value.toString(),
                color = null,
                onClick = { onClickConstant(index) },
                onClickRemove = { onClickRemoveConstant(index) }
            )
        }
    }
}

@Composable
fun FunctionRow(
    lhsName: String,
    text: String,
    color: Color?,
    onClick: () -> Unit,
    onClickRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick.invoke() }
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "$lhsName = ", style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontStyle = FontStyle.Italic
            ), color = color ?: MaterialTheme.colorScheme.primary
        )
        Text(
            text = text, style = TextStyle(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontStyle = FontStyle.Italic
            )
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onClickRemove() }) {
            Icon(
                imageVector = Icons.Rounded.Clear,
                contentDescription = "Remove function"
            )
        }
    }
}