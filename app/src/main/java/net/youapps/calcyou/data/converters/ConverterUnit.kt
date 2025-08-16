package net.youapps.calcyou.data.converters

import net.youapps.calcyou.data.Either


interface ConverterUnit<T> {
    val name: Either<Int, String>

    fun convertFrom(value: T): T
    fun convertTo(value: T): T

    fun convert(outputUnit: ConverterUnit<T>, value: T): T {
        return outputUnit.convertTo(this.convertFrom(value))
    }
}

inline fun <reified T> checkType(obj: Any): Boolean {
    return obj is T
}
