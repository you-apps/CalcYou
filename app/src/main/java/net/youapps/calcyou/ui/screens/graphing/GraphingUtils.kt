package net.youapps.calcyou.ui.screens.graphing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import net.youapps.calcyou.data.evaluator.Constant
import net.youapps.calcyou.data.evaluator.TrigonometricMode
import net.youapps.calcyou.data.graphing.Function
import net.youapps.calcyou.data.graphing.Window
import net.youapps.calcyou.data.graphing.unitToPxCoordinates
import net.youapps.calcyou.viewmodels.GraphViewModel
import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.floor

fun DrawScope.drawGridLines(window: Window, lineWidth: Float, gridLinesColor: Color) {
    val xRange = IntRange(
        ceil(window.xMin / window.xScale).toInt(),
        floor(window.xMax / window.xScale).toInt()
    )

    for (i in xRange) {
        if (i == 0) continue
        val xDraw =
            Offset(i * window.xScale, 0f).unitToPxCoordinates(window, size.width, size.height).x
        drawLine(gridLinesColor, Offset(xDraw, 0f), Offset(xDraw, size.height), lineWidth)
    }
    val yRange = IntRange(
        ceil(window.yMin / window.yScale).toInt(),
        floor(window.yMax / window.yScale).toInt()
    )

    for (i in yRange) {
        if (i == 0) continue
        val yDraw =
            Offset(0f, i * window.yScale).unitToPxCoordinates(window, size.width, size.height).y
        drawLine(gridLinesColor, Offset(0f, yDraw), Offset(size.width, yDraw), lineWidth)
    }
}

internal fun Float.toDisplayString(): String {
    return when {
        (this % 1f == 0f) && (abs(this) in 0.0001f..10000f) -> {
            this.toInt().toString()
        }

        abs(this) <= 0.0001 -> {
            "%.3e".format(this)
        }

        abs(this) < 10000 -> {
            this.toString()
        }

        else -> {
            "%.2e".format(this)
        }
    }
}

fun DrawScope.drawAxes(
    window: Window,
    lineWidth: Float,
    canvasScale: Float,
    textMeasurer: TextMeasurer,
    axesColor: Color
) {

    // y-axis
    val windowCenterInCanvas = Offset(0f, 0f).unitToPxCoordinates(window, size.width, size.height)
    drawLine(
        axesColor,
        Offset(windowCenterInCanvas.x, 0f),
        Offset(windowCenterInCanvas.x, size.height),
        lineWidth
    )
    // x-axis
    drawLine(
        axesColor,
        Offset(0f, windowCenterInCanvas.y),
        Offset(size.width, windowCenterInCanvas.y),
        lineWidth
    )

    // Ticks on x-axis
    val xTickRange = IntRange(
        ceil(window.xMin / window.xScale).toInt(), floor(window.xMax / window.xScale).toInt()
    )

    for (i in xTickRange) {
        if (i == 0) continue

        val xDisplayValue = (i * window.xScale).toDisplayString()
        val xDraw =
            Offset(i * window.xScale, 0f).unitToPxCoordinates(window, size.width, size.height).x
        val yDraw = Offset(0f, 0f).unitToPxCoordinates(window, size.width, size.height).y

//        val halfTickLength = 10f / canvasScale
//        drawLine(
//            axesColor,
//            Offset(xDraw, yDraw + halfTickLength),
//            Offset(xDraw, yDraw - halfTickLength),
//            lineWidth
//        )

        val textSize = 36 / canvasScale
        val textPadding = 20 / canvasScale

        val textLayoutResult = textMeasurer.measure(
            text = xDisplayValue,
            style = TextStyle(
                color = axesColor,
                fontSize = textSize.toSp(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium
            )
        )

        drawText(
            textLayoutResult,
            topLeft = Offset(
                xDraw - textLayoutResult.size.width / 2,
                yDraw + textPadding
            )
        )
    }

    val yTickRange = IntRange(
        ceil(window.yMin / window.yScale).toInt(), floor(window.yMax / window.yScale).toInt()
    )

    for (i in yTickRange) {
        if (i == 0) continue

        val yDisplayValue = (i * window.yScale).toDisplayString()
        val xDraw = Offset(0f, 0f).unitToPxCoordinates(window, size.width, size.height).x
        val yDraw =
            Offset(0f, i * window.yScale).unitToPxCoordinates(window, size.width, size.height).y

//        val halfTickLength = 10f / canvasScale
//        drawLine(
//            axesColor,
//            Offset(xDraw - halfTickLength, yDraw),
//            Offset(xDraw + halfTickLength, yDraw),
//            lineWidth
//        )

        val textSize = 36 / canvasScale
        val textPadding = 20 / canvasScale

        val textLayoutResult = textMeasurer.measure(
            yDisplayValue,
            style = TextStyle(
                color = axesColor,
                fontSize = textSize.toSp(),
                fontWeight = FontWeight.Medium
            )
        )

        drawText(
            textLayoutResult,
            topLeft = Offset(
                xDraw + textPadding,
                yDraw - textLayoutResult.size.height / 2
            )
        )
    }
}

fun DrawScope.graphAroundAsymptote(
    window: Window,
    function: Function,
    constants: List<Constant>,
    aX1: Float,
    aX2: Float,
    pDerivative: Float,
    depth: Int,
    lineWidth: Float,
    mode: TrigonometricMode
) {
    var previousDerivative = pDerivative
    val precision = 2
    for (j in 0 until precision) {
        val currentX = aX1 + (aX2 - aX1) * j / precision
        val nextX = aX1 + (aX2 - aX1) * (j + 1) / precision
        val currentY = function.execute(currentX, mode, constants) ?: 0f
        val nextY = function.execute(nextX, mode, constants) ?: 0f

        val currentDerivative = (nextY - currentY) / (nextX - currentX)
        if ((currentDerivative >= 0 && previousDerivative >= 0) || (currentDerivative <= 0 && previousDerivative <= 0)) {
            drawLine(
                function.color,
                Offset(currentX, currentY).unitToPxCoordinates(window, size.width, size.height),
                Offset(nextX, nextY).unitToPxCoordinates(window, size.width, size.height),
                lineWidth
            )
        } else {
            if (depth > 1) {
                graphAroundAsymptote(
                    window,
                    function,
                    constants,
                    currentX,
                    nextX,
                    previousDerivative,
                    depth - 1,
                    lineWidth,
                    mode
                )
            }
            return
        }
        previousDerivative = currentDerivative
    }
}

fun DrawScope.drawGraph(window: Window, function: Function, constants: List<Constant>, lineWidth: Float, mode: TrigonometricMode) {
    val resolution = 500
    var previousX = 0f
    var previousDerivative = 0f
    for (i in 0 until resolution) {
        val currentX = window.xMin + i / resolution.toFloat() * (window.xMax - window.xMin)
        val nextX = window.xMin + (i + 1) / resolution.toFloat() * (window.xMax - window.xMin)

        val currentY = function.execute(currentX, mode, constants) ?: 0f
        val nextY = function.execute(nextX, mode, constants) ?: 0f

        val currentDerivative = (nextY - currentY) / (nextX - currentX)
        if ((currentDerivative >= 0 && previousDerivative >= 0) || (currentDerivative <= 0 && previousDerivative <= 0)) {
            drawLine(
                function.color,
                Offset(currentX, currentY).unitToPxCoordinates(window, size.width, size.height),
                Offset(nextX, nextY).unitToPxCoordinates(window, size.width, size.height),
                lineWidth
            )
        } else {
            if (abs(previousDerivative) < abs(currentDerivative)) {
                graphAroundAsymptote(
                    window,
                    function,
                    constants,
                    currentX,
                    nextX,
                    previousDerivative,
                    20,
                    lineWidth,
                    mode
                )
                // If curve approaches asymptote from right side
            } else {
                graphAroundAsymptote(
                    window,
                    function,
                    constants,
                    nextX,
                    previousX,
                    currentDerivative,
                    20,
                    lineWidth,
                    mode
                )
            }
            drawLine(
                function.color,
                Offset(currentX, currentY).unitToPxCoordinates(window, size.width, size.height),
                Offset(nextX, currentY).unitToPxCoordinates(window, size.width, size.height),
                lineWidth
            )
        }
        previousDerivative = currentDerivative
        previousX = currentX
    }
}

fun DrawScope.renderCanvas(
    window: Window,
    vm: GraphViewModel,
    canvasScale: Float,
    textMeasurer: TextMeasurer,
    gridLinesColor: Color,
    axesColor: Color
) {
    val lineWidth = 5f / canvasScale
    window.findAutoScale()
    drawGridLines(window, lineWidth, gridLinesColor)
    drawAxes(window, lineWidth, canvasScale, textMeasurer, axesColor)

    vm.functions.forEach {
        // this might fail if we attempt to use a constant that no longer exists
        runCatching {
            drawGraph(window, it, vm.constants, lineWidth, vm.mode)
        }
    }
}