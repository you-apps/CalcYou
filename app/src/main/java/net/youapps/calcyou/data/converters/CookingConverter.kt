package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class CookingConverter: UnitConverter<Double> {


    override val units: List<ConverterUnit<Double>> = listOf(
        FactorUnit(R.string.liter, 1.0),
        FactorUnit(R.string.milliliter, 1E-3),
        FactorUnit(R.string.gallon, 3.785411784),
        FactorUnit(R.string.quart, 0.946352946),
        FactorUnit(R.string.pint, 0.473176473),
        FactorUnit(R.string.fluid_ounce, 0.02957352957),
        FactorUnit(R.string.tablespoon, 0.01478676479),
        FactorUnit(R.string.teaspoon, 0.00492892159),
        FactorUnit(R.string.cups, 0.236588),
        FactorUnit(R.string.pinch, 0.000308)
    )
}