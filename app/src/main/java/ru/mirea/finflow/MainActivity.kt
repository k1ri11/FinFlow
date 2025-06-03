package ru.mirea.finflow

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.auth.presentation.login.LoginNavScreen
import ru.mirea.auth.presentation.register.RegisterNavScreen
import ru.mirea.core.navigation.Screens
import ru.mirea.core.navigation.enterFromStartTransition
import ru.mirea.core.navigation.enterTransition
import ru.mirea.core.navigation.exitToEndTransition
import ru.mirea.core.navigation.exitTransition
import ru.mirea.core.navigation.navigator.NavigatorImpl
import ru.mirea.core.navigation.popEnterTransition
import ru.mirea.core.navigation.popExitTransition
import ru.mirea.core.presentation.CheckAuthViewModel
import ru.mirea.event.add_event.presentation.AddEventNavScreen
import ru.mirea.event.details.presentation.EventsDetailsNavScreen
import ru.mirea.event.event_list.presentation.EventsNavScreen
import ru.mirea.expense.presentation.AddSpendingNavScreen
import ru.mirea.feature.friends.presentation.FriendsNavScreen
import ru.mirea.profile.presentation.ProfileNavScreen
import ru.mirea.uikit.AppScaffold
import ru.mirea.uikit.components.navigation.BottomNavScreens
import ru.mirea.uikit.theme.FinFlowTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: NavigatorImpl

    private val checkAuthViewModel: CheckAuthViewModel by viewModels()
    private var isReady by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            !isReady
        }

        enableEdgeToEdge()
        setContent {
            val isAuthorized by checkAuthViewModel.isAuthorized.collectAsState()
            isAuthorized?.let {
                isReady = true

                FinFlowTheme {
                    if (isReady) {
                        AppNavigation(navigator, it)
                    }
                }
            }
        }
    }
}

@Composable
private fun AppNavigation(
    navigator: NavigatorImpl,
    isAuthorized: Boolean,
) {
    val navController = rememberNavController()
    navigator.navController = navController

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in BottomNavScreens.items.map { it.route }

    AppScaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavigation(navController = navController)
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = if (isAuthorized) BottomNavScreens.Home.route else Screens.Login.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            // Экраны авторизации
            composable(
                route = Screens.Login.route,
                enterTransition = { enterFromStartTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() }
            ) {
                LoginNavScreen(navigator = navigator)
            }
            composable(
                route = Screens.Register.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitTransition() },
                popEnterTransition = { popEnterTransition() },
                popExitTransition = { popExitTransition() }
            ) {
                RegisterNavScreen(navigator = navigator)
            }

            // Главные экраны с нижней навигацией
            composable(
                route = BottomNavScreens.Home.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitToEndTransition() }
            ) {
                EventsNavScreen(navigator = navigator)
            }
            composable(
                route = BottomNavScreens.Profile.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitToEndTransition() }
            ) {
                ProfileNavScreen()
            }
            composable(
                route = BottomNavScreens.Friends.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitToEndTransition() }
            ) {
                FriendsNavScreen()
            }

            // просто экраны
            composable(
                route = Screens.AddEvent.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitToEndTransition() }
            ) {
                AddEventNavScreen(navigator = navigator)
            }

            composable(
                route = Screens.EventDetails.route,
                arguments = listOf(
                    navArgument("eventId") {
                        type = androidx.navigation.NavType.IntType
                    }
                ),
                enterTransition = { enterTransition() },
                exitTransition = { exitToEndTransition() }
            ) {
                val eventId = it.arguments?.getInt("eventId") ?: -1
                EventsDetailsNavScreen(navigator = navigator, eventId = eventId)
            }

            composable(
                route = Screens.AddSpending.route,
                arguments = listOf(
                    navArgument("eventId") {
                        type = androidx.navigation.NavType.IntType
                    }
                ),
                enterTransition = { enterTransition() },
                exitTransition = { exitToEndTransition() }
            ) {
                val eventId = it.arguments?.getInt("eventId") ?: -1
                AddSpendingNavScreen(navigator = navigator, eventId = eventId)
            }
        }
    }
}


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
                            popUpTo(ru.mirea.uikit.components.navigation.BottomNavScreens.Home.route) {
                                saveState = true
                            }
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinFlowTheme {
    }
}