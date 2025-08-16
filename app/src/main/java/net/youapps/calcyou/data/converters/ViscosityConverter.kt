package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class ViscosityConverter : UnitConverter<Double> {
    override val units: List<ConverterUnit<Double>> = listOf(
        FactorUnit(R.string.pascal_second, 1.0),
        FactorUnit(R.string.centipoise, 0.01),
        FactorUnit(R.string.stoke, 1E4),
        FactorUnit(R.string.poiseuille, 1E9)
    )
}