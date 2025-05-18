package ru.mirea.core.navigation.navigator

import androidx.navigation.NavHostController
import javax.inject.Inject

class NavigatorImpl @Inject constructor() : Navigator {
    lateinit var navController: NavHostController

    override fun navigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
        }
    }

    override fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    override fun navigateAndPopUpTo(route: String, popUpTo: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUpTo) { inclusive = false }
        }
    }

    override fun popBackStack() {
        navController.popBackStack()
    }


    override fun navigateAndClearBackStack(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
} 