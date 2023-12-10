package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class EnergyConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.joule, 1.0),
        FactorUnit(R.string.kilojoule, 1E3),
        FactorUnit(R.string.megajoule, 1E6),
        FactorUnit(R.string.calorie, 4.184),
        FactorUnit(R.string.kilocalorie, 4.184E3),
        FactorUnit(R.string.grams_of_carbohydrate, 4.0 * 4.184),
        FactorUnit(R.string.grams_of_fat, 9.0 * 4.184),
        FactorUnit(R.string.grams_of_protein, 4.0 * 4.184)
    )
}