package net.youapps.calcyou.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopSearchBar
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import net.youapps.calcyou.R
import net.youapps.calcyou.data.converters.ConverterUnit
import net.youapps.calcyou.ui.components.FullscreenDialog
import net.youapps.calcyou.viewmodels.UnitConverterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ConverterUnitPickerScreen(
    converterViewModel: UnitConverterViewModel,
    availableUnits: List<ConverterUnit<T>>,
    selectedUnit: ConverterUnit<T>,
    onUnitSelected: (ConverterUnit<T>) -> Unit,
    onDismissRequest: () -> Unit
) {
    val context = LocalContext.current
    var query by remember {
        mutableStateOf("")
    }

    val favoriteUnitKeys by converterViewModel.favoriteUnits.collectAsState(emptyList())
    val (favored, notFavored) = availableUnits
        .filter { unitName(context, it.name).contains(query, ignoreCase = true) }
        .partition {
            converterViewModel.unitKey(it) in favoriteUnitKeys.map { it.key }
        }

    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()

    FullscreenDialog(onDismissRequest = onDismissRequest) {
        Scaffold(
            topBar = {
                val searchBarState = rememberSearchBarState()

                TopSearchBar(
                    state = searchBarState,
                    scrollBehavior = scrollBehavior,
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = query,
                            onQueryChange = { query = it },
                            onSearch = {

                            },
                            expanded = true,
                            onExpandedChange = {},
                            placeholder = { Text(stringResource(R.string.search)) },
                            leadingIcon = {
                                IconButton(onClick = onDismissRequest) {
                                    Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
                                }
                            }
                        )
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                item {
                    AnimatedVisibility(favored.isNotEmpty()) {
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp)
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(top = 8.dp),
                                text = stringResource(R.string.favorites),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Column {
                                for (unit in favored) {
                                    ConverterUnitRow(
                                        unit,
                                        isSelected = unit == selectedUnit,
                                        isFavorite = true,
                                        onClicked = {
                                            onUnitSelected(unit)
                                            onDismissRequest()
                                        },
                                        toggleIsFavorited = {
                                            converterViewModel.removeFavoriteUnit(unit)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                items(notFavored) { unit ->
                    ConverterUnitRow(
                        unit,
                        isSelected = unit == selectedUnit,
                        isFavorite = false,
                        onClicked = {
                            onUnitSelected(unit)
                            onDismissRequest()
                        },
                        toggleIsFavorited = {
                            converterViewModel.addFavoriteUnit(unit)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun <T> LazyItemScope.ConverterUnitRow(
    converterUnit: ConverterUnit<T>,
    isSelected: Boolean,
    isFavorite: Boolean,
    onClicked: () -> Unit,
    toggleIsFavorited: () -> Unit
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .animateItem()
            .clickable {
                onClicked()
            }
            .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant else Color.Transparent
            )
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = unitName(context, converterUnit.name)
        )

        IconButton(
            onClick = {
                toggleIsFavorited()
            }
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground,
                contentDescription = null
            )
        }
    }
}