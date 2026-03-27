package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class VolumeConverter : UnitConverter<Double> {
    // all units from the cooking converter are shown here as well
    private val cookingConverterDelegate = CookingConverter()

    override val units: List<ConverterUnit<Double>> = (listOf(
        FactorUnit(R.string.liter, 1.0),
        FactorUnit(R.string.milliliter, 1E-3),
        FactorUnit(R.string.kiloliter, 1E3),
        FactorUnit(R.string.cubic_meter, 1E3),
        FactorUnit(R.string.cubic_centimeter, 1E-3),
        FactorUnit(R.string.cubic_decimeter, 1.0)
    ) + cookingConverterDelegate.units).distinctBy { it.name }
}