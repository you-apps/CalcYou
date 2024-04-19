package net.youapps.calcyou.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.R
import net.youapps.calcyou.data.CalculatorEvent
import net.youapps.calcyou.data.SimpleOperator
import net.youapps.calcyou.data.SpecialOperator
import net.youapps.calcyou.ui.components.buttons.CalculatorButton
import net.youapps.calcyou.ui.components.buttons.CalculatorTextButton


val leftKeypad = arrayOf(
    arrayOf(SpecialOperator.Sin, SpecialOperator.Cos, SpecialOperator.Tan),
    arrayOf(SpecialOperator.Ln, SpecialOperator.Log, SpecialOperator.SquareRoot),
    arrayOf(SpecialOperator.PowerE, SpecialOperator.Square, SpecialOperator.Power),
    arrayOf(SpecialOperator.Absolute, SpecialOperator.PI, SpecialOperator.E),
)


val rightKeypad = arrayOf(
    arrayOf(SpecialOperator.ASin, SpecialOperator.ACos, SpecialOperator.ATan),
    arrayOf(SpecialOperator.SinH, SpecialOperator.CosH, SpecialOperator.TanH),
    arrayOf(SpecialOperator.ASinH, SpecialOperator.ACosH, SpecialOperator.ATanH),
    arrayOf(SpecialOperator.Power2, SpecialOperator.Cube, SpecialOperator.Factorial),
)
val combinedKeypad = arrayOf(
    arrayOf(SpecialOperator.Sin, SpecialOperator.Cos, SpecialOperator.Tan, SpecialOperator.ASin),
    arrayOf(
        SpecialOperator.Ln,
        SpecialOperator.Log,
        SpecialOperator.SquareRoot,
        SpecialOperator.ACos
    ),
    arrayOf(
        SpecialOperator.PowerE,
        SpecialOperator.Square,
        SpecialOperator.Power,
        SpecialOperator.ATan
    ),
    arrayOf(SpecialOperator.Absolute, SpecialOperator.PI, SpecialOperator.E, SpecialOperator.SinH),
    arrayOf(
        SpecialOperator.ASinH,
        SpecialOperator.ACosH,
        SpecialOperator.ATanH,
        SpecialOperator.CosH
    ),
    arrayOf(
        SpecialOperator.Power2,
        SpecialOperator.Cube,
        SpecialOperator.Factorial,
        SpecialOperator.TanH
    ),
)

@Composable
fun Keypad(
    onEvent: (CalculatorEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SwipePanels(
            start = { SideKeypad(onEvent = onEvent, keys = leftKeypad) },
            center = { CenterKeypad(onEvent) },
            end = { SideKeypad(onEvent = onEvent, keys = rightKeypad) })
    }
}

@Composable
fun CenterKeypad(
    onEvent: (CalculatorEvent) -> Unit
) {
    val buttonSpacing = 8.dp
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                iconRes = R.drawable.bracket_l,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.SpecialOperator(SpecialOperator.LBracket))
                }
            )
            CalculatorButton(
                iconRes = R.drawable.bracket_r,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.SpecialOperator(SpecialOperator.RBracket))
                }
            )
            CalculatorButton(
                iconRes = R.drawable.percent,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Percent))
                }
            )
            CalculatorButton(
                iconRes = R.drawable.divide,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Divide))
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "7",
                onClick = {
                    onEvent(CalculatorEvent.Number(7))
                }
            )
            CalculatorButton(
                text = "8",
                onClick = {
                    onEvent(CalculatorEvent.Number(8))
                }
            )
            CalculatorButton(
                text = "9",
                onClick = {
                    onEvent(CalculatorEvent.Number(9))
                }
            )
            CalculatorButton(
                iconRes = R.drawable.multiply,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Multiply))
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "4",
                onClick = {
                    onEvent(CalculatorEvent.Number(4))
                }
            )
            CalculatorButton(
                text = "5",
                onClick = {
                    onEvent(CalculatorEvent.Number(5))
                }
            )
            CalculatorButton(
                text = "6",
                onClick = {
                    onEvent(CalculatorEvent.Number(6))
                }
            )
            CalculatorButton(
                iconRes = R.drawable.minus,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Minus))
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "1",
                onClick = {
                    onEvent(CalculatorEvent.Number(1))
                }
            )
            CalculatorButton(
                text = "2",
                onClick = {
                    onEvent(CalculatorEvent.Number(2))
                }
            )
            CalculatorButton(
                text = "3",
                onClick = {
                    onEvent(CalculatorEvent.Number(3))
                }
            )
            CalculatorButton(
                iconRes = R.drawable.plus,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Plus))
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "0",
                onClick = {
                    onEvent(CalculatorEvent.Number(0))
                }
            )
            CalculatorButton(
                text = ".",
                onClick = {
                    onEvent(CalculatorEvent.Decimal)
                }
            )
            CalculatorButton(
                iconRes = R.drawable.delete,
                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                textColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    onEvent(CalculatorEvent.Delete)
                },
                onLongClick = {
                    onEvent(CalculatorEvent.DeleteAll)
                }
            )
            CalculatorButton(
                iconRes = R.drawable.equal,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Evaluate)
                }
            )
        }
    }
}

@Composable
fun CenterKeypadHorizontal(
    onEvent: (CalculatorEvent) -> Unit,
    textStyle: TextStyle = MaterialTheme.typography.displaySmall,
    iconSize: Dp = 48.dp
) {
    val buttonSpacing = 8.dp
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "7",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(7))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                text = "8",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(8))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                text = "9",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(9))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                iconRes = R.drawable.bracket_l,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.SpecialOperator(SpecialOperator.LBracket))
                },
                iconSize = iconSize
            )
            CalculatorButton(
                iconRes = R.drawable.bracket_r,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.SpecialOperator(SpecialOperator.RBracket))
                },
                iconSize = iconSize
            )

        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "4",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(4))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                text = "5",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(5))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                text = "6",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(6))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                iconRes = R.drawable.divide,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Divide))
                },
                iconSize = iconSize
            )
            CalculatorButton(
                iconRes = R.drawable.multiply,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Multiply))
                },
                iconSize = iconSize
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "1",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(1))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                text = "2",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(2))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                text = "3",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(3))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                iconRes = R.drawable.minus,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Minus))
                },
                iconSize = iconSize
            )
            CalculatorButton(
                iconRes = R.drawable.plus,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Plus))
                },
                iconSize = iconSize
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "0",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Number(0))
                },
                textStyle = textStyle
            )
            CalculatorButton(
                text = ".",
                square = false,
                onClick = {
                    onEvent(CalculatorEvent.Decimal)
                },
                textStyle = textStyle
            )
            CalculatorButton(
                iconRes = R.drawable.delete,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                textColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    onEvent(CalculatorEvent.Delete)
                },
                onLongClick = {
                    onEvent(CalculatorEvent.DeleteAll)
                },
                iconSize = iconSize
            )
            CalculatorButton(
                iconRes = R.drawable.percent,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Percent))
                },
                iconSize = iconSize
            )
            CalculatorButton(
                iconRes = R.drawable.equal,
                square = false,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                textColor = MaterialTheme.colorScheme.onPrimaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Evaluate)
                },
                iconSize = iconSize
            )
        }
    }
}


@Composable
fun SideKeypad(
    onEvent: (CalculatorEvent) -> Unit,
    keys: Array<Array<SpecialOperator>>,
    square: Boolean = true
) {
    val buttonSpacing = 8.dp
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        for (row in keys) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                for (operator in row) {
                    CalculatorTextButton(
                        text = operator.text,
                        square = square,
                        onClick = {
                            onEvent(CalculatorEvent.SpecialOperator(operator))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun SideKeypadHorizontal(
    onEvent: (CalculatorEvent) -> Unit,
    square: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.headlineMedium
) {
    val keys = combinedKeypad
    val buttonSpacing = 8.dp
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        for (row in keys) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                for (operator in row) {
                    CalculatorTextButton(
                        text = operator.text,
                        square = square,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        textStyle = textStyle,
                        onClick = {
                            onEvent(CalculatorEvent.SpecialOperator(operator))
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    Keypad(onEvent = {})
}
