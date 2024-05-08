package net.youapps.calcyou.data.graphing

import androidx.compose.ui.geometry.Offset

fun Offset.unitToPxCoordinates(
    window: Window,
    canvasWidth: Float,
    canvasHeight: Float
): Offset {
    return Offset(
        (x - window.xMin) / (window.xMax - window.xMin) * canvasWidth,
        (window.yMax - y) / (window.yMax - window.yMin) * canvasHeight
    )
}

fun Offset.pxToUnitCoordinates(
    window: Window,
    canvasWidth: Float,
    canvasHeight: Float
): Offset {
    return Offset(
        window.xMin + x * (window.xMax - window.xMin) / canvasWidth,
        window.yMax - y * (window.yMax - window.yMin) / canvasHeight
    )
}