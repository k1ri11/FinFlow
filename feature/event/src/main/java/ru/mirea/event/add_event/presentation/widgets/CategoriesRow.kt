package ru.mirea.event.add_event.presentation.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.mirea.event.add_event.domain.models.Category
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun CategoriesRow(
    categories: List<Category>,
    modifier: Modifier = Modifier,
    onCategorySelect: (Int) -> Unit,
    selectedCategoryId: Int? = null,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = categories,
            key = { it.id }
        ) { category ->
            CategoryItem(
                category = category,
                onSelect = onCategorySelect,
                selected = selectedCategoryId == category.id
            )
        }
    }
}

@Composable
fun CategoryItem(
    category: Category,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(FinFlowTheme.shapes.extraSmall)
            .background(if (selected) FinFlowTheme.colorScheme.background.secondary else FinFlowTheme.colorScheme.background.primary)
            .border(
                width = 1.dp,
                color = FinFlowTheme.colorScheme.border.brand,
                FinFlowTheme.shapes.extraSmall
            )
            .clickable { onSelect(category.id) }
            .padding(horizontal = 8.dp, vertical = 4.dp),
    ) {
//        AsyncImage(
//            model = category.icon.externalUuid,
//            contentDescription = null,
//            modifier = Modifier.size(20.dp),
//        )
        Text(
            text = category.name,
            style = FinFlowTheme.typography.bodyMedium,
            color = FinFlowTheme.colorScheme.text.brand,
        )
    }
}