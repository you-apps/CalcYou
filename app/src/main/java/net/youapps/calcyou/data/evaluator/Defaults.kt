package net.youapps.calcyou.data.evaluator

import java.security.InvalidParameterException
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.acosh
import kotlin.math.asin
import kotlin.math.asinh
import kotlin.math.atan
import kotlin.math.atan2
import kotlin.math.atanh
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.cosh
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sin
import kotlin.math.sinh
import kotlin.math.sqrt
import kotlin.math.tan
import kotlin.math.tanh
import kotlin.math.truncate

object Defaults {
    val defaultOperatorOrder: List<List<Operator>> = listOf(
        listOf(Operator.POW),
        listOf(Operator.DIV, Operator.MUL, Operator.MOD),
        listOf(Operator.SUB, Operator.ADD),
    )
    val defaultRightAssociativeOps: Set<String> = setOf("**")
    val defaultGenericConstants: Map<String, Double> = mapOf(
        "PI" to Math.PI,
        "PI_2" to Math.PI / 2,
        "LOG2E" to log2(Math.E),
        "DEG" to Math.PI / 180f,
        "E" to Math.E
    )
    val defaultVarNameChars: Set<Char> =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_$".toCharArray().toSet()
    val defaultFuncNameChars: List<Char> = "fghijklmnopqrstuvw".toList()
    val defaultConstantNameChars: List<Char> = "abcdeyz".toList()

    fun getDefaultGenericFunctions(): Map<String, EvalFunctionBlock> {
        return mapOf(
            "ABS" to { args ->
                abs(args.first())
            },
            "CEILING" to { args ->
                ceil(args.first())
            },
            "EXP" to { args ->
                exp(args.first())
            },
            "FLOOR" to { args ->
                floor(args.first())
            },
            "LN" to fn@{ args ->
                if (args.size == 2) {
                    val arg1 = args[0]
                    val arg2 = args[1]

                    return@fn log(arg1, arg2)
                } else if (args.size == 1) {
                    return@fn ln(args.first())
                }
                throw InvalidParameterException()
            },
            "LOG" to fn@{ args ->
                if (args.size == 2) {
                    val arg1 = args[0]
                    val arg2 = args[1]

                    return@fn log(arg1, arg2)
                } else if (args.size == 1) {
                    return@fn ln(args.first())
                }
                throw InvalidParameterException()
            },
            "LOG2" to { args ->
                log2(args.first())
            },
            "LOG10" to { args ->
                log10(args.first())
            },
            "MAX" to fn@{ args ->
                var v = args[0]
                for (arg in args) {
                    val narg = arg
                    if (narg > v)
                        v = narg
                }
                v
            },
            "MIN" to fn@{ args ->
                var v = args[0]
                for (arg in args) {
                    val narg = arg
                    if (narg < v)
                        v = narg
                }
                v
            },
            "POW" to fn@{ args ->
                if (args.size == 2) {
                    val arg1 = args[0]
                    val arg2 = args[1]
                    return@fn arg1.pow(arg2)
                }
                throw InvalidParameterException()
            },
            "ROUND" to { args ->
                round(args.first())
            },
            "SIGN" to { args ->
                if (args.first() < 0) -1.0 else if (args.first() > 0) 1.0 else 0.0
            },
            "SQRT" to { args ->
                sqrt(args.first())
            },
            "TRUNCATE" to { args ->
                truncate(args.first())
            },
            "FAC" to fn@{ args ->
                val num = floor(args.first()).toInt()
                if (num < 0) throw InvalidParameterException()

                (2..num).fold(1.0) { a, b -> a * b}
            },

            // Trigonometric functions start here
            "SIN" to { args ->
                sin(args.first())
            },
            "ASIN" to { args ->
                asin(args.first())
            },
            "SINH" to { args ->
                sinh(args.first())
            },
            "ASINH" to { args ->
                asinh(args.first())
            },
            "COS" to { args ->
                cos(args.first())
            },
            "ACOS" to { args ->

                acos(args.first())
            },
            "COSH" to { args ->
                cosh(args.first())
            },
            "ACOSH" to { args ->
                acosh(args.first())
            },
            "TAN" to { args ->
                tan(args.first())
            },
            "ATAN" to { args ->
                atan(args.first())
            },
            "ATAN2" to { args ->
                val arg1 = args[0]
                val arg2 = args[1]
                if (args.size != 2) {
                    throw InvalidParameterException()
                }
                atan2(arg1, arg2)
            },
            "TANH" to { args ->
                tanh(args.first())
            },
            "ATANH" to { args ->
                atanh(args.first())
            },
        )
    }
}