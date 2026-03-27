package net.youapps.calcyou.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import net.youapps.calcyou.CalculatorApplication
import net.youapps.calcyou.data.Either
import net.youapps.calcyou.data.converters.ConverterUnit
import net.youapps.calcyou.persistence.FavoriteUnitsRepository

class UnitConverterViewModel(application: Application): AndroidViewModel(application) {
    private val favoriteUnitsRepository = FavoriteUnitsRepository(application)

    val currentCategoryKey = MutableStateFlow<String?>(null)
    val favoriteUnits = combine(favoriteUnitsRepository.unitsFlow, currentCategoryKey) { favoriteUnits, category ->
        favoriteUnits.firstOrNull { it.key == category }?.favoritesList.orEmpty()
    }

    fun <T> unitKey(unit: ConverterUnit<T>): String {
        return when (val name = unit.name) {
            is Either.Left -> application.resources.getResourceEntryName(name.value)
            is Either.Right -> name.value
        }
    }

    fun <T> addFavoriteUnit(unit: ConverterUnit<T>) = viewModelScope.launch {
        favoriteUnitsRepository.addFavoriteUnit(currentCategoryKey.value!!, unitKey(unit))
    }

    fun <T> removeFavoriteUnit(unit: ConverterUnit<T>) = viewModelScope.launch {
        favoriteUnitsRepository.removeFavoriteUnit(currentCategoryKey.value!!, unitKey(unit))
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as CalculatorApplication
                UnitConverterViewModel(application)
            }
        }
    }
}