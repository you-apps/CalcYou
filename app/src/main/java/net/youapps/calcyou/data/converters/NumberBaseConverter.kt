package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class NumberBaseConverter() : UnitConverter<String?> {
    override val units: List<ConverterUnit<String?>> = listOf(
        RadixBaseUnit(R.string.decimal, 10),
        RadixBaseUnit(R.string.binary, 2),
        RadixBaseUnit(R.string.octal, 8),
        RadixBaseUnit(R.string.hexadecimal, 16),
    )

    override fun convertAll(
        value: String?,
        unit: ConverterUnit<String?>
    ): List<Pair<ConverterUnit<String?>, String?>> {
        return super.convertAll(value, unit).filter { it.second != null }
    }
}