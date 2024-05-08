package net.youapps.calcyou.ui.screens.graphing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import net.youapps.calcyou.data.graphing.pxToUnitCoordinates
import net.youapps.calcyou.viewmodels.GraphViewModel

@Composable
fun CanvasView(vm: GraphViewModel) {
    Box(Modifier.background(MaterialTheme.colorScheme.background)) {
        val textMeasurer = rememberTextMeasurer()
        val drawModifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                awaitEachGesture {
                    val downEvent = awaitFirstDown()
                    if (downEvent.pressed != downEvent.previousPressed) downEvent.consume()
                    do {
                        val event = awaitPointerEvent()
                        if (event.changes.size == 1) {
                            val pan = event.changes
                                .first()
                                .positionChange()
                            with(vm.window) {

                                val xPan = pan.x * (xMax - xMin) / size.width.toFloat()
                                val yPan = pan.y * (yMax - yMin) / size.height.toFloat()

                                xMin -= xPan
                                xMax -= xPan
                                yMin += yPan
                                yMax += yPan
                            }
                            event.changes
                                .first()
                                .consume()
                        } else if (event.changes.size > 1) {
                            with(vm.window) {

                                val zoom = event.calculateZoom()
                                val center = event
                                    .calculateCentroid()
                                    .pxToUnitCoordinates(
                                        vm.window,
                                        size.width.toFloat(),
                                        size.height.toFloat()
                                    )

                                xMin = center.x + (xMin - center.x) / zoom
                                xMax = center.x + (xMax - center.x) / zoom
                                yMin = center.y + (yMin - center.y) / zoom
                                yMax = center.y + (yMax - center.y) / zoom

                                val pan = event
                                    .calculatePan()


                                val xPan = pan.x * (xMax - xMin) / size.width.toFloat()
                                val yPan = pan.y * (yMax - yMin) / size.height.toFloat()

                                xMin -= xPan
                                xMax -= xPan
                                yMin += yPan
                                yMax += yPan
                            }
                            event.changes.forEach { it.consume() }
                        }
                        vm.window = vm.window
                    } while (event.changes.any { it.pressed })
                }
            }
        val gridLinesColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f)
        val gridAxesColor = MaterialTheme.colorScheme.onBackground
        Canvas(modifier = drawModifier) {
            val scale = 1f
            scale(scale) {
                renderCanvas(vm.window, vm, scale, textMeasurer, gridLinesColor, gridAxesColor)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CanvasViewPreview() {
    CanvasView(vm = viewModel())
}