package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class DigitalStorageConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.bit, 1.0),
        FactorUnit(R.string._byte, 8.0),
        FactorUnit(R.string.kilobit, 1E3),
        FactorUnit(R.string.kilobyte, 8E3),
        FactorUnit(R.string.kibibyte, 8.0 * 1024),
        FactorUnit(R.string.megabit, 1E6),
        FactorUnit(R.string.megabyte, 8E6),
        FactorUnit(R.string.mebibyte, 8.0 * 1024 * 1024),
        FactorUnit(R.string.gigabit, 1E9),
        FactorUnit(R.string.gigabyte, 8E9),
        FactorUnit(R.string.gigibyte, 8.0 * 1024 * 1024 * 1024),
        FactorUnit(R.string.terabit, 1E12),
        FactorUnit(R.string.terabyte, 8E12),
        FactorUnit(R.string.tebibyte, 8.0 * 1024 * 1024 * 1024 * 1024),
        FactorUnit(R.string.petabit, 1E15),
        FactorUnit(R.string.petabyte, 8E15),
        FactorUnit(R.string.pebibyte, 8.0 * 1024 * 1024 * 1024 * 1024 * 1024),
        FactorUnit(R.string.exabit, 1E18),
        FactorUnit(R.string.exabyte, 8E18),
        FactorUnit(R.string.exbibyte, 8.0 * 1024 * 1024 * 1024 * 1024 * 1024 * 1024)
    )
}