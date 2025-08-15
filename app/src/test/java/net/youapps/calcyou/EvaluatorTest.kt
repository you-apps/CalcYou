package net.youapps.calcyou

import net.youapps.calcyou.data.evaluator.CompiledExpression
import net.youapps.calcyou.data.evaluator.ExpressionEvaluator
import net.youapps.calcyou.data.evaluator.TrigonometricMode
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.exp
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.random.Random

internal class EvaluatorTest {

    val testCases = mapOf(
        "3 - 5 * x" to { x: Double -> 3 - 5 * x },
        "sin(x)" to { x: Double -> sin(x) },
        "cos(x)" to { x: Double -> cos(x) },
        "tan(x)" to { x: Double -> tan(x) },
        "sqrt(x)" to { x: Double -> sqrt(x) },
        "abs(x)" to { x: Double -> abs(x) },
        "log(x)" to { x: Double -> ln(x) },
        "exp(x)" to { x: Double -> exp(x) },
        "log10(x)" to { x: Double -> log10(x) },
        "log(x,2)" to { x: Double -> log(x, 2.0) },
        "sin(x) + cos(x)" to { x: Double -> sin(x) + cos(x) },
        "sin(x) * cos(x)" to { x: Double -> sin(x) * cos(x) },
        "sin(x) / cos(x)" to { x: Double -> sin(x) / cos(x) },
        "sin(x) - cos(x)" to { x: Double -> sin(x) - cos(x) },
        "sin(x) ** 2" to { x: Double -> sin(x) * sin(x) },
        "x * x" to { x: Double -> x * x },
        "x ** 2" to { x: Double -> x.pow(2) },
        "pow(x,3)" to { x: Double -> x.pow(3) },
        "2" to { _: Double -> 2.0 },
        "sqrt(4)" to { _: Double -> sqrt(4.0) },
        "round(x)" to { x: Double -> round(x) },
        "sin(2*PI)" to { _: Double -> sin(2 * Math.PI) },
        "x*(5+3*x)" to { x: Double -> x * (5 + 3 * x) },
        "sin(cos(x))" to { x: Double -> sin(cos(x)) },
        "sin(cos(5 + x) * 2)" to { x: Double -> sin(cos(5 + x) * 2) },
        "sin(asin(cos(x)))" to { x: Double -> sin(asin(cos(x))) },
        "abs(-x ** 4)" to { x: Double -> abs(-x.pow(4)) },
        "20x+7" to { x: Double -> 20 * x + 7 },
        "20 x x / 20 x" to { x: Double -> 20 * x * x / 20 * x }
    )
    val random = Random(System.currentTimeMillis())

    @Test
    fun `execute() should return correct answer for compiled expressions`() {
        testCases.forEach { expression, func ->
            val argument = random.nextDouble()
            val compiled: CompiledExpression = ExpressionEvaluator.compile(expression)

            val answer = compiled.execute(TrigonometricMode.RADIAN, listOf("x" to argument))

            assertNotNull(answer)

            val expected = func(argument)
            assertEquals(expected, answer!!, 1e-6)
        }
    }
}