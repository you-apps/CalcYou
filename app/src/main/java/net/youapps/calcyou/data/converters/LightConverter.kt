package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class LightConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.lumen, 1.0),
        FactorUnit(R.string.candela, 1.0),
        FactorUnit(R.string.lux, 1.0 / 3.14159), // Lumen per square meter
        FactorUnit(R.string.footcandle, 10.7639104167097)
    )
}