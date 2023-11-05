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
        replacementMap["Infinity"] = "∞"
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