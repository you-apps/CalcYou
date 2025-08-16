package net.youapps.calcyou.data

import android.content.Context
import java.text.DecimalFormatSymbols
import java.util.Locale


class Tokenizer(context: Context) {
    private val replacementMap: MutableMap<String, String> = HashMap()

    init {
        var locale: Locale = context.resources.configuration.locale
        locale = Locale.Builder()
            .setLocale(locale)
            .setUnicodeLocaleKeyword("nu", "latn")
            .build()
        val symbols = DecimalFormatSymbols(locale)
        val zeroDigit: Char = symbols.zeroDigit
        replacementMap["."] = symbols.decimalSeparator.toString()
        for (i in 0..9) {
            replacementMap[i.toString()] = (i + zeroDigit.code).toChar().toString()
        }

        SimpleOperator.values().forEach {
            replacementMap[it.value] = it.text
        }

        // users are allowed to use the unicode symbols at the right, however to not have duplicated
        // logic inside the code, we're normalizing these representations
        replacementMap.putAll(mapOf(
            "Infinity" to "∞",
            "sqrt" to "√",
            "**" to "^",
            "pi" to "π",
            // fractions
            "(1/2)" to "½",
            "(1/3)" to "⅓",
            "(2/3)" to "⅔",
            "(1/4)" to "¼",
            "(3/4)" to "¾",
            "(1/5)" to "⅕",
            "(2/5)" to "⅖",
            "(3/5)" to "⅗",
            "(4/5)" to "⅘",
            "(1/6)" to "⅙",
            "(5/6)" to "⅚",
            "(1/7)" to "⅐",
            "(1/8)" to "⅛",
            "(3/8)" to "⅜",
            "(5/8)" to "⅝",
            "(7/8)" to "⅞",
            "(1/9)" to "⅑",
            "(1/10)" to "⅒",
            // exponents
            "**1" to "¹",
            "**2" to "²",
            "**3" to "³",
            "**4" to "⁴",
            "**5" to "⁵",
            "**6" to "⁶",
            "**7" to "⁷",
            "**8" to "⁸",
            "**9" to "⁹",
            "**0" to "⁰"
        ))
    }

    fun getNormalizedExpression(expr: String): String {
        var exp = expr
        for ((key, value) in replacementMap) {
            exp = exp.replace(value, key)
        }
        return exp
    }

    fun getLocalizedExpression(expr: String): String {
        var exp = expr
        for ((key, value) in replacementMap) {
            exp = exp.replace(key, value)
        }
        return exp
    }
}