package net.youapps.calcyou.data

sealed interface CalculatorEvent {
    object Decimal : CalculatorEvent
    object Evaluate : CalculatorEvent
    object Delete : CalculatorEvent
    object DeleteAll : CalculatorEvent
    data class Number(val number: Int) : CalculatorEvent
    data class Operator(val simpleOperator: SimpleOperator) : CalculatorEvent
    data class SpecialOperator(val specialOperator: net.youapps.calcyou.data.SpecialOperator) :
        CalculatorEvent
}
