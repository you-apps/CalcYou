package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class ForceConverter : UnitConverter<Double> {
    override val units: List<ConverterUnit<Double>> = listOf(
        FactorUnit(R.string.newton, 1.0),
        FactorUnit(R.string.kilonewton, 1E3),
        FactorUnit(R.string.dyne, 1E-5),
        FactorUnit(R.string.pound_force, 4.448222),
        FactorUnit(R.string.ounce_force, 0.2780139)
    )
}