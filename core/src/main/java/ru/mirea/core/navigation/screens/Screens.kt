package ru.mirea.core.navigation.screens

interface Destination {
    val route: String
    val destination: String
}

sealed class Screens : Destination {
    data object Login : Screens() {
        override val route = "login"
        override val destination = "login"
    }

    data object Register : Screens() {
        override val route = "register"
        override val destination = "register"
    }

    data object AddEvent : Screens() {
        override val route = "add_event"
        override val destination = "add_event"
    }

    data object EmptyScreen : Screens() {
        override val route = "empty"
        override val destination = "empty"
    }
} 