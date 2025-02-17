package net.youapps.calcyou.data.graphing

import androidx.compose.ui.graphics.Color

data class Function(
    val expression: String,
    val color: Color,
    val name: String,
    val compiled: CompiledExpression
) {
    fun execute(variableX: Float, constants: List<Constant>): Float? {
        val variables = listOf(
            "x" to variableX.toDouble(),
            *constants.map { it.identifier.toString() to it.value }.toTypedArray()
        )
        return compiled.execute(variables)?.toFloat()
    }

    companion object {
        fun create(expression: String, color: Color, functionName: String): Function {
            val compiled: CompiledExpression = Evaluator.compile(expression)
            return Function(
                expression, color, functionName, compiled
            )
        }
    }
}