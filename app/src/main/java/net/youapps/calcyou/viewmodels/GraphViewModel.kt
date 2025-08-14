package net.youapps.calcyou.viewmodels

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.neverEqualPolicy
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import net.youapps.calcyou.R
import net.youapps.calcyou.data.evaluator.Defaults
import net.youapps.calcyou.data.evaluator.ExpressionEvaluator
import net.youapps.calcyou.data.graphing.Function
import net.youapps.calcyou.data.evaluator.Constant
import net.youapps.calcyou.data.graphing.Window
import net.youapps.calcyou.ui.components.rainbowColors
import java.text.ParseException
import kotlin.random.Random

class GraphViewModel(private val application: Application) : AndroidViewModel(application) {
    private val context: Context
        get() = application.applicationContext
    var window by mutableStateOf(Window(), neverEqualPolicy())
    val functions = mutableStateListOf<Function>()
    val constants = mutableStateListOf<Constant>()

    private val random = Random(System.currentTimeMillis())
    var isError by mutableStateOf(true)
        private set
    var errorText by mutableStateOf(context.getString(R.string.expression_is_empty))
        private set

    var selectedFunctionIndex by mutableIntStateOf(-1)
        private set
    var functionName by mutableStateOf(Defaults.defaultFuncNameChars.first().toString())
    var functionColor by mutableStateOf(rainbowColors.first())

    var expression by mutableStateOf("")

    var selectedConstantIndex by mutableIntStateOf(-1)

    fun updateSelectedFunction(index: Int) {
        selectedFunctionIndex = index
        if (index == -1) {
            expression = ""
            functionName = getFuncName(functions.size)
            functionColor = getFuncColor(functions.size)
            return
        }
        val function = functions[index]
        functionName = function.name
        functionColor = function.color
        expression = function.expression
    }

    fun checkExpression(expression: String) {
        if (expression.isBlank()) {
            errorText = context.getString(R.string.expression_is_empty)
            isError = true
            return
        }
        try {
            val compiled = ExpressionEvaluator.compile(expression)
            val variables = listOf(
                "x" to random.nextDouble(),
                *constants.map { it.identifier.toString() to it.value }.toTypedArray()
            )
            compiled.execute(variables)
            isError = false
        } catch (e: ParseException) {
            errorText = e.message ?: context.getString(R.string.error_parsing_expression)
            isError = true
        } catch (e: Exception) {
            errorText = context.getString(R.string.error_parsing_expression)
            isError = true
        }
    }

    fun addFunction(expression: String, color: Color, functionName: String) {
        if (selectedFunctionIndex != -1) {
            functions[selectedFunctionIndex] = Function.create(expression, color, functionName)
            updateSelectedFunction(-1)
            return
        }
        functions.add(Function.create(expression, color, functionName))
    }

    fun removeFunction(index: Int) {
        functions.removeAt(index)

        // update the other functions following the delete one with new function names and colors
        for (i in index until functions.size) {
            functions[index] = functions[index].copy(name = getFuncName(i), color = getFuncColor(i))
        }
    }

    private fun getFuncName(index: Int): String {
        return Defaults.defaultFuncNameChars[
            index % Defaults.defaultFuncNameChars.size
        ].toString()
    }

    private fun getFuncColor(index: Int): Color {
        return rainbowColors[index % rainbowColors.size]
    }
}