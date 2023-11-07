package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class FrequencyConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.hertz, 1.0),
        FactorUnit(R.string.kilohertz, 1E3),
        FactorUnit(R.string.megahertz, 1E6),
        FactorUnit(R.string.gigahertz, 1E9)
    )
}