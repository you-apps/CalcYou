package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class PressureConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.pascal, 1.0),
        FactorUnit(R.string.kilopascal, 1E3),
        FactorUnit(R.string.megapascal, 1E6),
        FactorUnit(R.string.gigapascal, 1E9),
        FactorUnit(R.string.bar, 1E5),
        FactorUnit(R.string.millibar, 1E2),
        FactorUnit(R.string.atmosphere, 101325.0),
        FactorUnit(R.string.psi, 6894.757293168) // Pounds per Square Inch
    )
}