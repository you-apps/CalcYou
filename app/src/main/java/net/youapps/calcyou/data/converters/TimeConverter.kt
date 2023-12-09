package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class TimeConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.second, 1.0),
        FactorUnit(R.string.minute, 60.0),
        FactorUnit(R.string.hour, 3.6E3),
        FactorUnit(R.string.day, 8.64E4),
        FactorUnit(R.string.week, 6.048E5)
    )
}