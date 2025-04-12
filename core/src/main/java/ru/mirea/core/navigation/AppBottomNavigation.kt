package ru.mirea.core.navigation

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.mirea.core.navigation.screens.BottomNavScreens
import ru.mirea.uikit.theme.FinFlowTheme

@Composable
fun AppBottomNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier.clip(FinFlowTheme.shapes.large),
        containerColor = FinFlowTheme.colorScheme.background.tertiary,
        contentColor = FinFlowTheme.colorScheme.icon.secondary
    ) {
        BottomNavScreens.items.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedIndicatorColor = Color.Transparent
                ),
                selected = selected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(BottomNavScreens.Home.route) {
                                saveState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = screen.icon,
                        contentDescription = screen.title,
                        tint = if (selected) FinFlowTheme.colorScheme.icon.brand
                        else FinFlowTheme.colorScheme.icon.secondary
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = if (selected) FinFlowTheme.colorScheme.icon.brand
                        else FinFlowTheme.colorScheme.icon.secondary
                    )
                }
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun NavigationBarPreviewLight() {
    FinFlowTheme {
        AppBottomNavigation(
            navController = rememberNavController(),
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL)
@Composable
private fun NavigationBarPreviewDark() {
    FinFlowTheme {
        AppBottomNavigation(
            navController = rememberNavController(),
        )
    }
}