package ru.mirea.core.navigation.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavScreens(
    val route: String,
    val title: String,
    val icon: ImageVector,
) {
    data object Home : BottomNavScreens(
        route = "home",
        title = "Главная",
        icon = Icons.Default.Home
    )

    data object Analytics : BottomNavScreens(
        route = "notifications",
        title = "Уведомления",
        icon = Icons.Default.Notifications
    )

    data object Friends : BottomNavScreens(
        route = "friends",
        title = "Друзья",
        icon = Icons.Default.Person
    )

    data object Profile : BottomNavScreens(
        route = "profile",
        title = "Профиль",
        icon = Icons.Default.Person
    )

    companion object {
        val items = listOf(Home, Analytics, Friends, Profile)
    }
} 