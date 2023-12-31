package net.youapps.calcyou.data

import org.javia.arity.Symbols
import org.javia.arity.Util


class Evaluator(private val tokenizer: Tokenizer) {
    private val symbols: Symbols = Symbols()

    fun evaluate(expr: String): String? {
        var expr = expr
        expr = tokenizer.getNormalizedExpression(expr)
        if (expr.isEmpty()) {
            return null
        }
        // remove any trailing operators
        while (expr.isNotEmpty() && "+-/*".indexOf(expr[expr.length - 1]) != -1) {
            expr = expr.substring(0, expr.length - 1)
        }
        return try {
            val result: Double = symbols.eval(expr)
            if (java.lang.Double.isNaN(result)) {
                null
            } else {
                tokenizer.getLocalizedExpression(
                    Util.doubleToString(result, MAX_DIGITS, ROUNDING_DIGITS)
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    companion object {
        private const val MAX_DIGITS = 12
        private const val ROUNDING_DIGITS = 5

    }
}