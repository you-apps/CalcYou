package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class FuelConverter : UnitConverter<Double> {
    override val units: List<ConverterUnit<Double>> = listOf(
        FactorUnit(R.string.liter, 1.0),
        FactorUnit(R.string.gallon_us, 3.78541),
        FactorUnit(R.string.gallon_uk, 4.54609),
        FactorUnit(R.string.barrel, 158.98729)
    )
}