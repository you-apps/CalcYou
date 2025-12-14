package net.youapps.calcyou.ui.components

import android.content.res.Configuration
import androidx.core.os.ConfigurationCompat
import java.text.DecimalFormatSymbols
import java.util.Locale

class NumberFormatter(configuration: Configuration) {
    val locale: Locale = ConfigurationCompat.getLocales(configuration).get(0)?: Locale.getDefault()
    private val symbols = DecimalFormatSymbols.getInstance(locale)
    private val thousandsSep = symbols.groupingSeparator.toString()
    private val bitGroupingSymb = " "
    val decimalSep = symbols.decimalSeparator.toString()

    // Escape special regex characters if needed
    private val escapedDecimalSep = Regex.escape(decimalSep)
    //The regex assumes that the decimal part of numbers won't have more than 99 digits
    private val regex = """(?<!$escapedDecimalSep\d{0,99})\d(?=(\d{3})+(?!\d))""".toRegex()
    private val hexRegex = """(?<!$escapedDecimalSep[0-9A-Fa-f]{0,99})[0-9A-Fa-f](?=([0-9A-Fa-f]{2})+(?![0-9A-Fa-f]))""".toRegex()
    private val binRegex = """(?<!$escapedDecimalSep[0-1]{0,99})[0-1](?=([0-1]{4})+(?![0-1]))""".toRegex()

    fun formatNumberFromString(input: String): String{
        return input.replace(".", decimalSep)
            .replace(regex, "$0$thousandsSep")
    }
    fun formatOctNumberFromString(input: String): String{
        return input.replace(".", decimalSep)
            .replace(regex, "$0$bitGroupingSymb")
    }
    fun formatHexNumberFromString(input: String): String{
        return input.replace(".", decimalSep)
            .replace(hexRegex, "$0$bitGroupingSymb")
    }
    fun formatBinNumberFromString(input: String): String{
        return input.replace(".", decimalSep)
            .replace(binRegex, "$0$bitGroupingSymb")
    }
}

