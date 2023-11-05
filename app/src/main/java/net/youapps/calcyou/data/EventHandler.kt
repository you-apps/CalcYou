package net.youapps.calcyou.data

class EventHandler(
    private val tokenizer: Tokenizer,
    private val evaluator: Evaluator,
    private val onUpdateHistory: (String) -> Unit
) {
    private var expression = ""

    fun processEvent(event: CalculatorEvent) {
        when (event) {
            is CalculatorEvent.Number -> {
                if (expression == "0") expression = ""
                if (expression.lastOrNull()?.isOperator() == false && expression.lastOrNull()
                        ?.isDigit() == false
                ) {
                    expression += "*"
                }
                expression += event.number
            }

            is CalculatorEvent.Operator -> {
                expression += event.simpleOperator.value
            }

            CalculatorEvent.Delete -> {
                expression = expression.dropLast(1)
                if (expression.isEmpty()) expression = "0"
            }

            CalculatorEvent.DeleteAll -> {
                expression = "0"
            }

            CalculatorEvent.Decimal -> {
                expression += if (expression.last().isDigit()) {
                    "."
                } else {
                    "0."
                }
            }

            CalculatorEvent.Evaluate -> {
                onUpdateHistory(tokenizer.getLocalizedExpression(expression))
                expression = evaluator.evaluate(expression) ?: "Error"
            }

            CalculatorEvent.SwitchPolarity -> {
                expression = if (expression.firstOrNull() == '-') {
                    expression.drop(1)
                } else {
                    "-($expression"
                }
            }

            is CalculatorEvent.SpecialOperator -> {
                if (expression == "0") expression = ""
                when (val op = event.specialOperator) {
                    SpecialOperator.Bracket -> {
                        expression += if (expression.lastOrNull()
                                ?.isDigit() == false && expression.lastOrNull() != ')'
                        ) {
                            "("
                        } else {
                            val lBracketCount = expression.filter { it == '(' }.length
                            val rBracketCount = expression.filter { it == ')' }.length
                            if (lBracketCount > rBracketCount) {
                                ")"
                            } else {
                                "*("
                            }
                        }
                    }

                    SpecialOperator.Square, SpecialOperator.Absolute, SpecialOperator.Power, SpecialOperator.Cube, SpecialOperator.Factorial -> {
                        expression += op.value
                    }

                    else -> {
                        if (expression.lastOrNull()?.isDigit() == true) {
                            expression += "*"
                        }
                        expression += op.value
                    }


                }
            }
        }
    }

    private fun Char.isOperator(): Boolean {
        return SimpleOperator.values().map { it.value }
            .any { this.toString() == it } || this == '(' || this == '|'
    }

    fun getDisplayText(): String {
        return tokenizer.getLocalizedExpression(expression)
    }
}