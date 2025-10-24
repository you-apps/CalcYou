package net.youapps.calcyou.data

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import net.youapps.calcyou.data.evaluator.TrigonometricMode
import java.lang.Exception

class EventHandler(
    private val context: Context,
    private val onUpdateHistory: (String) -> Unit
) {
    private val tokenizer = Tokenizer(context)
    private val evaluator = FormattingEvaluator(tokenizer)

    fun processEvent(
        event: CalculatorEvent,
        currentText: TextFieldValue,
        mode: MutableState<TrigonometricMode>
    ): TextFieldValue {
        return when (event) {
            is CalculatorEvent.Number -> {
                currentText.insertText(event.number.toString())
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
                currentText.insertText(".")
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
    }

    fun evaluateResult(currentText: String, mode: MutableState<TrigonometricMode>): String? {
        return evaluator.evaluate(currentText, mode.value)
    }
}