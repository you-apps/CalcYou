/*
 This code is a modified version of https://github.com/danielgindi/KotlinEval
 The original source code is licensed under MIT LICENSE
 */

package net.youapps.calcyou.data.graphing

import java.security.InvalidParameterException
import kotlin.math.abs
import kotlin.math.acos
import kotlin.math.asin
import kotlin.math.atan
import kotlin.math.atan2
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

    fun getDefaultGenericFunctions(): Map<String, EvalFunctionBlock> {
        return mapOf(
            "ABS" to { args ->
                abs(args.first())
            },
            "ACOS" to { args ->

                acos(args.first())
            },
            "ASIN" to { args ->

                asin(args.first())
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
            "CEILING" to { args ->
                ceil(args.first())
            },
            "COS" to { args ->
                cos(args.first())
            },
            "COSH" to { args ->
                cosh(args.first())
            },
            "EXP" to { args ->
                exp(args.first())
            },
            "FLOOR" to { args ->
                floor(args.first())
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
            "SIN" to { args ->
                sin(args.first())
            },
            "SINH" to { args ->
                sinh(args.first())
            },
            "SQRT" to { args ->
                sqrt(args.first())
            },
            "TAN" to { args ->
                tan(args.first())
            },
            "TANH" to { args ->
                tanh(args.first())
            },
            "TRUNCATE" to { args ->
                truncate(args.first())
            })
    }
}