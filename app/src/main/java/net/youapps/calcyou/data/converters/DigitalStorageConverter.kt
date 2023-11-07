package net.youapps.calcyou.data.converters

import net.youapps.calcyou.R

class DigitalStorageConverter : UnitConverter {
    override val units: List<ConverterUnit> = listOf(
        FactorUnit(R.string.bit, 1.0),
        FactorUnit(R.string._byte, 8.0),
        FactorUnit(R.string.kilobyte, 8.0 * 1024),
        FactorUnit(R.string.megabyte, 8.0 * 1024 * 1024),
        FactorUnit(R.string.gigabyte, 8.0 * 1024 * 1024 * 1024),
        FactorUnit(R.string.terabyte, 8.0 * 1024 * 1024 * 1024 * 1024),
        FactorUnit(R.string.petabyte, 8.0 * 1024 * 1024 * 1024 * 1024 * 1024),
        FactorUnit(R.string.exabyte, 8.0 * 1024 * 1024 * 1024 * 1024 * 1024)
    )
}