package ru.mirea.core.navigation.screens

import androidx.annotation.DrawableRes
import ru.mirea.uikit.R

sealed class BottomNavScreens(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int,
) {
    data object Home : BottomNavScreens(
        route = "home",
        title = "Главная",
        icon = R.drawable.ic_home
    )

    data object Analytics : BottomNavScreens(
        route = "notifications",
        title = "Уведомления",
        icon = R.drawable.ic_calendar
    )

    data object Friends : BottomNavScreens(
        route = "friends",
        title = "Друзья",
        icon = R.drawable.ic_friends
    )

    data object Profile : BottomNavScreens(
        route = "profile",
        title = "Профиль",
        icon = R.drawable.ic_user
    )

    companion object {
        val items = listOf(Home, Analytics, Friends, Profile)
    }
} 