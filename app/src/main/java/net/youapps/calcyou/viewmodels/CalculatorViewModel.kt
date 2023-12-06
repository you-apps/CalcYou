package net.youapps.calcyou.viewmodels

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import net.youapps.calcyou.CalculatorApplication
import net.youapps.calcyou.data.CalculatorEvent
import net.youapps.calcyou.data.Evaluator
import net.youapps.calcyou.data.EventHandler
import net.youapps.calcyou.data.Tokenizer

class CalculatorViewModel(context: Context) : ViewModel() {

    private val tokenizer = Tokenizer(context)
    private val evaluator = Evaluator(tokenizer)
    private val eventHandler =
        EventHandler(tokenizer, evaluator, onUpdateHistory = this::onUpdateHistory)
    var displayText: String by mutableStateOf("0")

    var history: MutableList<String> = mutableStateListOf()

    fun setExpression(text: String) {
        eventHandler.setExperssion(text)
        displayText = eventHandler.getDisplayText()
    }

    fun onEvent(event: CalculatorEvent) {
        eventHandler.processEvent(event)
        displayText = eventHandler.getDisplayText()
    }

    private fun onUpdateHistory(item: String) {
        history.add(item)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as CalculatorApplication
                CalculatorViewModel(application)
            }
        }
    }
}