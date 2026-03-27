package net.youapps.calcyou.persistence

import android.content.Context
import kotlinx.coroutines.flow.map
import net.you_apps.calcyou.FavoriteUnitCategory
import net.you_apps.calcyou.Unit

class FavoriteUnitsRepository(val context: Context) {
    val dataStore get() = context.favoriteUnitsDataStore

    val unitConverterCategoriesFlow = dataStore.data.map { it.categoriesList }

    suspend fun addFavoriteUnit(categoryKey: String, unitKey: String) {
        dataStore.updateData { data ->
            var builder = data.toBuilder()
            val unit = Unit.newBuilder().setKey(unitKey).build()

            // add category if it doesn't exist yet
            if (data.categoriesList.none { it.key == categoryKey }) {
                builder = builder.addCategories(
                    FavoriteUnitCategory.newBuilder()
                        .setKey(categoryKey)
                        .addFavorites(unit)
                        .build()
                )
            } else {
                val categoryIndex = data.categoriesList.indexOfFirst { category ->
                    category.key == categoryKey
                }
                val updatedCategory = data.categoriesList[categoryIndex].toBuilder()
                updatedCategory.addFavorites(unit)

                builder.setCategories(categoryIndex, updatedCategory)
            }

            builder.build()
        }
    }

    suspend fun removeFavoriteUnit(categoryKey: String, unitKey: String) {
        dataStore.updateData { data ->
            val categoryIndex = data.categoriesList.indexOfFirst { category ->
                category.key == categoryKey
            }

            val category = data.categoriesList[categoryIndex].toBuilder()
            val unitIndex = category.favoritesList.indexOfFirst { it.key == unitKey }
            category.removeFavorites(unitIndex)

            data.toBuilder()
                .setCategories(categoryIndex, category)
                .build()
        }
    }

    suspend fun setSelectedUnit(categoryKey: String, unitKey: String?) {
        dataStore.updateData { data ->
            var builder = data.toBuilder()

            // although it's not currently needed, it's possible to clear
            // the selected unit to reset it to default
            val unit = unitKey?.let { Unit.newBuilder().setKey(it).build() }

            // add category if it doesn't exist yet
            if (data.categoriesList.none { it.key == categoryKey }) {
                builder = builder.addCategories(
                    FavoriteUnitCategory.newBuilder()
                        .setKey(categoryKey)
                        .setSelectedUnit(unit)
                        .build()
                )
            } else {
                val categoryIndex = data.categoriesList.indexOfFirst { category ->
                    category.key == categoryKey
                }
                val updatedCategory = data.categoriesList[categoryIndex].toBuilder()
                updatedCategory.setSelectedUnit(unit)

                builder.setCategories(categoryIndex, updatedCategory)
            }

            builder.build()
        }
    }
}