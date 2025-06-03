package ru.mirea.core.navigation

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

    data object EventDetails : Screens() {
        override val route = "event_details/{eventId}"
        override val destination = "event_details"
        fun createRoute(eventId: Int) = "event_details/$eventId"
    }

    data object EmptyScreen : Screens() {
        override val route = "empty"
        override val destination = "empty"
    }

    data object AddSpending : Screens() {
        override val route = "add_spending/{eventId}"
        override val destination = "add_spending"
        fun createRoute(eventId: Int) = "add_spending/$eventId"
    }
} 