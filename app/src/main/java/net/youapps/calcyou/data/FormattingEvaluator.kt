package net.youapps.calcyou.data

import net.youapps.calcyou.data.evaluator.ExpressionEvaluator
import net.youapps.calcyou.data.evaluator.MathUtil
import net.youapps.calcyou.data.evaluator.TrigonometricMode

/**
 * Evaluates expressions and returns the result as a formatted, localized string.
 */
class FormattingEvaluator(private val tokenizer: Tokenizer) {
    fun evaluate(expr: String, mode: TrigonometricMode): String? {
        var expr = tokenizer.getNormalizedExpression(expr)
        if (expr.isEmpty()) {
            return null
        }
        // remove any trailing operators
        while (expr.isNotEmpty() && "+-/*".contains(expr[expr.length - 1])) {
            expr = expr.substring(0, expr.length - 1)
        }

        // add missing parentheses to the end of the expression
        val parenthesesBalance = expr.count { it == '(' } - expr.count { it == ')' }
        if (parenthesesBalance != 0) {
            expr += ")".repeat(parenthesesBalance)
        }

        return try {
            val result = ExpressionEvaluator.compile(expr).execute(mode) ?: return null
            if (result.isNaN()) {
                null
            } else {
                val shortened = MathUtil.doubleToString(result)
                tokenizer.getLocalizedExpression(shortened)
            }
        } catch (_: Exception) {
            null
        }
    }
}