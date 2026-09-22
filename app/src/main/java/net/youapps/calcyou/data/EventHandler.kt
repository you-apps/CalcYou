package net.youapps.calcyou.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import net.youapps.calcyou.data.evaluator.TrigonometricMode
import java.text.DecimalFormatSymbols

class EventHandler(
    private val context: Context,
    private val onUpdateHistory: (String) -> Unit
) {
    private val tokenizer = Tokenizer(context)
    private val evaluator = FormattingEvaluator(tokenizer)
    private val numberFormat = DecimalFormatSymbols.getInstance()
    private var mostRecentEvent: CalculatorEvent? = null

    /**
     * Inserts the text of the pressed button into the text field.
     * If the previous calculation already finished and the user inserts
     * a new number or decimal point, a new calculation is started.
     */
    fun processEvent(
        event: CalculatorEvent,
        currentText: TextFieldValue,
        mode: MutableState<TrigonometricMode>
    ): TextFieldValue {
        val result = when (event) {
            is CalculatorEvent.Number -> {
                val newText = event.number.toString()

                if (mostRecentEvent == CalculatorEvent.Evaluate) {
                    TextFieldValue(
                        text = newText,
                        selection = TextRange(newText.length)
                    )
                } else {
                    currentText.insertText(newText)
                }
            }

            is CalculatorEvent.Operator -> {
                currentText.insertText(event.simpleOperator.text)
            }

            CalculatorEvent.Delete -> {
                currentText.backSpace()
            }

            CalculatorEvent.DeleteAll -> {
                TextFieldValue("")
            }

            CalculatorEvent.Decimal -> {
                val newText = numberFormat.decimalSeparator.toString()
                if (mostRecentEvent == CalculatorEvent.Evaluate) {
                    TextFieldValue(
                        text = newText,
                        selection = TextRange(newText.length)
                    )
                } else {
                    currentText.insertText(newText)
                }
            }

            CalculatorEvent.Evaluate -> {
                onUpdateHistory(currentText.text)
                val newText = try {
                    evaluateResult(currentText.text, mode) ?: "Error"
                } catch (e: Exception) {
                    Toast.makeText(context, e.message ?: e.javaClass.simpleName, Toast.LENGTH_LONG)
                        .show()
                    "Error"
                }
                TextFieldValue(
                    newText,
                    selection = TextRange(newText.length)
                )
            }

            is CalculatorEvent.SpecialOperator -> {
                currentText.insertText(event.specialOperator.value)
            }

            CalculatorEvent.ToggleTrigonometricMode -> {
                mode.value =
                    if (mode.value == TrigonometricMode.RADIAN) TrigonometricMode.DEGREE else TrigonometricMode.RADIAN

                currentText
            }
        }
        mostRecentEvent = event
        return result
    }

    fun evaluateResult(currentText: String, mode: MutableState<TrigonometricMode>): String? {
        return evaluator.evaluate(currentText, mode.value)
    }
}
