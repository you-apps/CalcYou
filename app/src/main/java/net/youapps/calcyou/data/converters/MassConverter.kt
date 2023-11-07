package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class MassConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.kilogram, 1.0),
        FactorUnit(R.string.gram, 1E-3),
        FactorUnit(R.string.milligram, 1E-6),
        FactorUnit(R.string.tonne, 1E3),
        FactorUnit(R.string.pound, 0.45359237),
        FactorUnit(R.string.ounce, 0.028349523125)
    )
}