package net.youapps.calcyou.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "⌫",
                backgroundColor = MaterialTheme.colorScheme.tertiaryContainer,
                textColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Delete)
                },
                onLongClick = {
                    onEvent(CalculatorEvent.DeleteAll)
                }
            )

            CalculatorButton(
                text = SpecialOperator.Bracket.text,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.SpecialOperator(SpecialOperator.Bracket))
                }
            )

            CalculatorButton(
                text = SimpleOperator.Percent.text,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Percent))
                }
            )

            CalculatorButton(
                text = SimpleOperator.Divide.text,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Divide))
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                text = SimpleOperator.Multiply.text,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Multiply))
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                text = SimpleOperator.Minus.text,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Minus))
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
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
                text = SimpleOperator.Plus.text,
                backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onClick = {
                    onEvent(CalculatorEvent.Operator(SimpleOperator.Plus))
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            CalculatorButton(
                text = "+/-",
                onClick = {
                    onEvent(CalculatorEvent.SwitchPolarity)
                }
            )
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
                text = "=",
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
fun SideKeypad(
    onEvent: (CalculatorEvent) -> Unit,
    keys: Array<Array<SpecialOperator>>
) {
    val buttonSpacing = 8.dp
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        for (row in keys) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
            ) {
                for (operator in row) {
                    CalculatorTextButton(
                        text = operator.text,
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
