package net.youapps.calcyou.data.converters

import net.youapps.calcyou.data.Either

class RadixBaseUnit(stringRes: Int, val base: Int):  ConverterUnit<String?> {
    override val name: Either<Int, String> = Either.Left(stringRes)

    override fun convertFrom(value: String?): String? {
        if (value == null) return null

        return runCatching { value.toInt(base).toString() }
            .getOrNull()
    }

    override fun convertTo(value: String?): String? {
        if (value == null) return null

        return runCatching { value.toInt().toString(radix = base) }
            .getOrNull()
    }
}