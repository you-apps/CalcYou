package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class TorqueConverter : UnitConverter<Double> {
    override val units: List<ConverterUnit<Double>> = listOf(
        FactorUnit(R.string.newton_meter, 1.0),
        FactorUnit(R.string.kilonewton_meter, 1E3),
        FactorUnit(R.string.pound_foot, 1.3558179),
        FactorUnit(R.string.ounce_inch, 0.08333333)
    )
}