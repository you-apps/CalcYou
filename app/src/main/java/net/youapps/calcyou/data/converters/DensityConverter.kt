package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class DensityConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.kilogram_per_cubic_meter, 1.0),
        FactorUnit(R.string.gram_per_cubic_centimeter, 1E3),
        FactorUnit(R.string.pound_per_cubic_foot, 16.018463),
        FactorUnit(R.string.pound_per_gallon, 0.11982642)
    )
}