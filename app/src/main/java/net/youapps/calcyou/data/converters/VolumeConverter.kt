package net.youapps.calcyou.data.converters

class VolumeConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit("liter", 1.0),
        FactorUnit("milliliter", 1E-3),
        FactorUnit("kiloliter", 1E3),
        FactorUnit("cubic meter", 1E3),
        FactorUnit("cubic centimeter", 1E-3),
        FactorUnit("cubic decimeter", 1.0),
        FactorUnit("gallon", 3.785411784),
        FactorUnit("quart", 0.946352946),
        FactorUnit("pint", 0.473176473),
        FactorUnit("fluid ounce", 0.02957352957),
        FactorUnit("tablespoon", 0.01478676479),
        FactorUnit("teaspoon", 0.00492892159)
    )
}