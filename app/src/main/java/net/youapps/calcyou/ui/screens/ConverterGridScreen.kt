package net.youapps.calcyou.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.Destination
import net.youapps.calcyou.ui.components.ConverterCard

@Composable
fun ConverterGridScreen(onNavigate: (Destination) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp)
    ) {
        items(Destination.Converter.doubleConverters.toList() + Destination.Converter.stringConverters) { converter ->
            ConverterCard(icon = converter.icon, title = converter.resId, onClick = {
                onNavigate(converter)
            })
        }
    }
}