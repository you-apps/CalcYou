package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class AreaConverter : UnitConverter<Double> {
    override val units: List<ConverterUnit<Double>> = listOf(
        FactorUnit(R.string.square_meter, 1.0),
        FactorUnit(R.string.square_kilometer, 1E6),
        FactorUnit(R.string.square_centimeter, 1E-4),
        FactorUnit(R.string.square_millimeter, 1E-6),
        FactorUnit(R.string.hectare, 1E4),
        FactorUnit(R.string.acre, 4046.8564224),
        FactorUnit(R.string.square_yard, 0.83612736),
        FactorUnit(R.string.square_foot, 0.09290304)
    )
}