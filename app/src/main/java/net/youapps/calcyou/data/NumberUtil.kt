package net.youapps.calcyou.data

import kotlin.math.abs
import kotlin.math.min

/**
 * Contains static helper methods for formatting double values.
 */
object Util {
    const val LEN_UNLIMITED: Int = 100
    const val FLOAT_PRECISION: Int = -1

    /**
     * Returns an approximation with no more than maxLen chars.
     *
     *
     * This method is not public, it is called through doubleToString,
     * that's why we can make some assumptions about the format of the string,
     * such as assuming that the exponent 'E' is upper-case.
     *
     * @param str    the value to truncate (e.g. "-2.898983455E20")
     * @param maxLen the maximum number of characters in the returned string
     * @return a truncation no longer then maxLen (e.g. "-2.8E20" for maxLen=7).
     */
    fun sizeTruncate(str: String, maxLen: Int): String? {
        if (maxLen == LEN_UNLIMITED) {
            return str
        }
        val ePos = str.lastIndexOf('E')
        val tail = if (ePos != -1) str.substring(ePos) else ""
        val tailLen = tail.length
        val headLen = str.length - tailLen
        val maxHeadLen = maxLen - tailLen
        val keepLen = min(headLen, maxHeadLen)
        if (keepLen < 1 || (keepLen < 2 && str.isNotEmpty() && str[0] == '-')) {
            return str // impossible to truncate
        }
        var dotPos = str.indexOf('.')
        if (dotPos == -1) {
            dotPos = headLen
        }
        if (dotPos > keepLen) {
            var exponent = if (ePos != -1) str.substring(ePos + 1).toInt() else 0
            val start = if (str[0] == '-') 1 else 0
            exponent += dotPos - start - 1
            val newStr = str.substring(0, start + 1) + '.' + str.substring(
                start + 1,
                headLen
            ) + 'E' + exponent
            return sizeTruncate(newStr, maxLen)
        }
        return str.substring(0, keepLen) + tail
    }

    /**
     * Rounds by dropping roundingDigits of double precision
     * (similar to 'hidden precision digits' on calculators),
     * and formats to String.
     *
     * @param v              the value to be converted to String
     * @param roundingDigits the number of 'hidden precision' digits (e.g. 2).
     * @return a String representation of v
     */
    fun doubleToString(v: Double, roundingDigits: Int): String {
        val absv = abs(v)
        val str =
            if (roundingDigits == FLOAT_PRECISION) absv.toFloat().toString() else absv.toString()
        val buf = StringBuffer(str)
        var roundingStart =
            if (roundingDigits <= 0 || roundingDigits > 13) 17 else (16 - roundingDigits)

        val ePos = str.lastIndexOf('E')
        var exp = if (ePos != -1) str.substring(ePos + 1).toInt() else 0
        if (ePos != -1) {
            buf.setLength(ePos)
        }
        var len = buf.length

        //remove dot
        var dotPos = 0
        while (dotPos < len && buf.get(dotPos) != '.') {
            ++dotPos
        }
        exp += dotPos
        if (dotPos < len) {
            buf.deleteCharAt(dotPos)
            --len
        }

        //round
        var p = 0
        while (p < len && buf.get(p) == '0') {
            ++roundingStart
            ++p
        }

        if (roundingStart < len) {
            if (buf.get(roundingStart) >= '5') {
                var p = roundingStart - 1
                while (p >= 0 && buf.get(p) == '9') {
                    buf.setCharAt(p, '0')
                    --p
                }
                if (p >= 0) {
                    buf.setCharAt(p, (buf.get(p).code + 1).toChar())
                } else {
                    buf.insert(0, '1')
                    ++roundingStart
                    ++exp
                }
            }
            buf.setLength(roundingStart)
        }

        //re-insert dot
        if ((exp < -5) || (exp > 10)) {
            buf.insert(1, '.')
            --exp
        } else {
            for (i in len until exp) {
                buf.append('0')
            }
            for (i in exp..0) {
                buf.insert(0, '0')
            }
            buf.insert(if (exp <= 0) 1 else exp, '.')
            exp = 0
        }
        len = buf.length

        //remove trailing dot and 0s.
        var tail = len - 1
        while (tail >= 0 && buf.get(tail) == '0') {
            buf.deleteCharAt(tail)
            --tail
        }
        if (tail >= 0 && buf.get(tail) == '.') {
            buf.deleteCharAt(tail)
        }

        if (exp != 0) {
            buf.append('E').append(exp)
        }
        if (v < 0) {
            buf.insert(0, '-')
        }
        return buf.toString()
    }

    /**
     * Renders a real number to a String (for user display).
     *
     * @param maxLen   the maximum total length of the resulting string
     * @param rounding the number of final digits to round
     */
    fun doubleToString(x: Double, maxLen: Int, rounding: Int): String? {
        return sizeTruncate(doubleToString(x, rounding), maxLen)
    }
}