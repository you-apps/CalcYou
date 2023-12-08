package net.youapps.calcyou.data

import android.content.Context
import androidx.compose.ui.text.input.TextFieldValue

class EventHandler(
    context: Context,
    private val onUpdateHistory: (String) -> Unit
) {
    private val tokenizer = Tokenizer(context)
    private val evaluator = Evaluator(tokenizer)

    fun processEvent(event: CalculatorEvent, currentText: TextFieldValue): TextFieldValue {
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
                TextFieldValue(
                    evaluator.evaluate(
                        tokenizer.getNormalizedExpression(
                            currentText.text
                        )
                    ) ?: "Error"
                )
            }

            is CalculatorEvent.SpecialOperator -> {
                currentText.insertText(event.specialOperator.value)
            }
        }
    }
}