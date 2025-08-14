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
import kotlin.math.cosh
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.ln
import kotlin.math.log
import kotlin.math.log10
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.round
import kotlin.math.sinh
import kotlin.math.sqrt
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

    /**
     * Converts the given double [value] into radian mode.
     *
     * @param value double that's in the trigonometric mode specified by the  [mode] parameter
     * @param mode whether the input is radian or degree
     */
    private fun trigonometricModeToRadian(value: Double, mode: TrigonometricMode): Double {
        if (mode == TrigonometricMode.RADIAN) return value;

        return defaultGenericConstants["DEG"]!! * value;
    }

    fun getDefaultGenericFunctions(): Map<String, EvalFunctionBlock> {
        return mapOf(
            "ABS" to { args, _ ->
                abs(args.first())
            },
            "CEILING" to { args, _ ->
                ceil(args.first())
            },
            "EXP" to { args, _ ->
                exp(args.first())
            },
            "FLOOR" to { args, _ ->
                floor(args.first())
            },
            "LN" to fn@{ args, _ ->
                if (args.size == 2) {
                    val arg1 = args[0]
                    val arg2 = args[1]

                    return@fn log(arg1, arg2)
                } else if (args.size == 1) {
                    return@fn ln(args.first())
                }
                throw InvalidParameterException()
            },
            "LOG" to fn@{ args, _ ->
                if (args.size == 2) {
                    val arg1 = args[0]
                    val arg2 = args[1]

                    return@fn log(arg1, arg2)
                } else if (args.size == 1) {
                    return@fn ln(args.first())
                }
                throw InvalidParameterException()
            },
            "LOG2" to { args, _ ->
                log2(args.first())
            },
            "LOG10" to { args, _ ->
                log10(args.first())
            },
            "MAX" to fn@{ args, _ ->
                var v = args[0]
                for (arg in args) {
                    val narg = arg
                    if (narg > v)
                        v = narg
                }
                v
            },
            "MIN" to fn@{ args, _ ->
                var v = args[0]
                for (arg in args) {
                    val narg = arg
                    if (narg < v)
                        v = narg
                }
                v
            },
            "POW" to fn@{ args, _ ->
                if (args.size == 2) {
                    val arg1 = args[0]
                    val arg2 = args[1]
                    return@fn arg1.pow(arg2)
                }
                throw InvalidParameterException()
            },
            "ROUND" to { args, _ ->
                round(args.first())
            },
            "SIGN" to { args, _ ->
                if (args.first() < 0) -1.0 else if (args.first() > 0) 1.0 else 0.0
            },
            "SQRT" to { args, _ ->
                sqrt(args.first())
            },
            "TRUNCATE" to { args, _ ->
                truncate(args.first())
            },
            "FAC" to fn@{ args, _ ->
                val num = floor(args.first()).toInt()
                if (num < 0) throw InvalidParameterException()

                (2..num).fold(1.0) { a, b -> a * b}
            },

            // Trigonometric functions start here
            "SIN" to { args, mode ->
                MathUtil.sin(trigonometricModeToRadian(args.first(), mode))
            },
            "ASIN" to { args, mode ->
                asin(trigonometricModeToRadian(args.first(), mode))
            },
            "SINH" to { args, mode ->
                sinh(trigonometricModeToRadian(args.first(), mode))
            },
            "ASINH" to { args, mode ->
                asinh(trigonometricModeToRadian(args.first(), mode))
            },
            "COS" to { args, mode ->
                MathUtil.cos(trigonometricModeToRadian(args.first(), mode))
            },
            "ACOS" to { args, mode ->
                acos(trigonometricModeToRadian(args.first(), mode))
            },
            "COSH" to { args, mode ->
                cosh(trigonometricModeToRadian(args.first(), mode))
            },
            "ACOSH" to { args, mode ->
                acosh(trigonometricModeToRadian(args.first(), mode))
            },
            "TAN" to { args, mode ->
                MathUtil.tan(trigonometricModeToRadian(args.first(), mode))
            },
            "ATAN" to { args, mode ->
                atan(trigonometricModeToRadian(args.first(), mode))
            },
            "ATAN2" to { args, mode ->
                val arg1 = trigonometricModeToRadian(args[0], mode)
                val arg2 = trigonometricModeToRadian(args[1], mode)
                if (args.size != 2) {
                    throw InvalidParameterException()
                }
                atan2(arg1, arg2)
            },
            "TANH" to { args, mode ->
                tanh(trigonometricModeToRadian(args.first(), mode))
            },
            "ATANH" to { args, mode ->
                atanh(trigonometricModeToRadian(args.first(), mode))
            },
        )
    }
}