/*
 * Copyright (C) 2006-2009 Mihai Preda.
 * Copyright (C) 2025 You Apps
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.youapps.calcyou.data.evaluator

import kotlin.math.abs
import kotlin.math.exp
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Contains static helper methods for formatting double values.
 */
object MathUtil {
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
    fun sizeTruncate(str: String, maxLen: Int): String {
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
    fun doubleToString(x: Double, maxLen: Int = MAX_DIGITS, rounding: Int = ROUNDING_DIGITS): String {
        return sizeTruncate(doubleToString(x, rounding), maxLen)
    }

    private fun isPiMultiple(x: Double): Boolean {
        val d = x / Math.PI
        return d == floor(d)
    }

    /**
     * Sine wrapper that fixes IEEE754 inaccuracy around result value 0.
     */
    fun sin(x: Double): Double {
        return if (isPiMultiple(x)) 0.0 else kotlin.math.sin(x)
    }

    /**
     * Cosine wrapper that fixes IEEE754 inaccuracy around result value 0.
     */
    fun cos(x: Double): Double {
        return if (isPiMultiple(x - Math.PI / 2)) 0.0 else kotlin.math.cos(x)
    }

    /**
     * Tangent wrapper that fixes IEEE754 inaccuracy around result value 0.
     */
    fun tan(x: Double): Double {
        return if (isPiMultiple(x)) 0.0 else kotlin.math.tan(x)
    }

    /**
     * @param x must be >= 0, otherwise this results in undefined behavior
     */
    fun factorial(x: Double): Double {
        // naive (bad performing) implementation of factorials
        // nevertheless it should be fast enough to not cause any visible delays
        if (x < 170) return (2..x.toInt()).fold(1.0) { a, b -> a * b }

        // Gosper's Approximation, see https://mathworld.wolfram.com/StirlingsApproximation.html
        return (sqrt((2 * x + 1 / 3) * Math.PI) * x.pow(x) * exp((-x)))
    }

    private const val MAX_DIGITS = 12
    private const val ROUNDING_DIGITS = 5
}