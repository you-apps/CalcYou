package net.youapps.calcyou.data.converters

class RadixBaseUnit(override val name: Int, val base: Int):  ConverterUnit<String?> {
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