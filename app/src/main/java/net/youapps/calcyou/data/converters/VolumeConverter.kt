package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class VolumeConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.liter, 1.0),
        FactorUnit(R.string.milliliter, 1E-3),
        FactorUnit(R.string.kiloliter, 1E3),
        FactorUnit(R.string.cubic_meter, 1E3),
        FactorUnit(R.string.cubic_centimeter, 1E-3),
        FactorUnit(R.string.cubic_decimeter, 1.0),
        FactorUnit(R.string.gallon, 3.785411784),
        FactorUnit(R.string.quart, 0.946352946),
        FactorUnit(R.string.pint, 0.473176473),
        FactorUnit(R.string.fluid_ounce, 0.02957352957),
        FactorUnit(R.string.tablespoon, 0.01478676479),
        FactorUnit(R.string.teaspoon, 0.00492892159)
    )
}