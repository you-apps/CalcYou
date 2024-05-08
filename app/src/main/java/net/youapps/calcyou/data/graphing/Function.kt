package net.youapps.calcyou.data.graphing

import androidx.compose.ui.graphics.Color

class Function(
    val expression: String,
    val color: Color,
    val function: (Float) -> Float?
) {

    companion object {
        fun create(expression: String, color: Color): Function {
            val compiled: CompiledExpression = Evaluator.compile(expression)
            return Function(
                expression, color
            ) { value ->
                compiled.execute("x" to value.toDouble())?.toFloat()
            }
        }
    }
}