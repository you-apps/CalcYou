package net.youapps.calcyou.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import net.youapps.calcyou.CalculatorApplication

class ConverterViewModel(context: Context) : ViewModel() {

    var textFieldValue by mutableStateOf("")

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as CalculatorApplication
                ConverterViewModel(application)
            }
        }
    }
}