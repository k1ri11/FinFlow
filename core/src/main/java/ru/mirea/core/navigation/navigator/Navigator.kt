package ru.mirea.core.navigation.navigator

interface Navigator {
    fun navigate(route: String)
    fun navigateAndPopUp(route: String, popUp: String)
    fun navigateAndPopUpTo(route: String, popUpTo: String)
    fun popBackStack()
    fun navigateAndClearBackStack(route: String)
} 