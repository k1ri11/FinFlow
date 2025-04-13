package ru.mirea.finflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.auth.presentation.login.LoginNavScreen
import ru.mirea.auth.presentation.register.RegisterNavScreen
import ru.mirea.core.navigation.AppBottomNavigation
import ru.mirea.core.navigation.enterFromStartTransition
import ru.mirea.core.navigation.enterTransition
import ru.mirea.core.navigation.exitToEndTransition
import ru.mirea.core.navigation.exitTransition
import ru.mirea.core.navigation.navigator.NavigatorImpl
import ru.mirea.core.navigation.popEnterTransition
import ru.mirea.core.navigation.popExitTransition
import ru.mirea.core.navigation.screens.BottomNavScreens
import ru.mirea.core.navigation.screens.Screens
import ru.mirea.core.presentation.AppScaffold
import ru.mirea.core.presentation.CheckAuthViewModel
import ru.mirea.expense.presentation.ExpenseScreen
import ru.mirea.profile.presentation.ProfileNavScreen
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
                ExpenseScreen()
            }
            composable(
                route = BottomNavScreens.Analytics.route,
                enterTransition = { enterTransition() },
                exitTransition = { exitToEndTransition() }
            ) {
                Box(modifier = Modifier.fillMaxSize()) { Text("экран аналитики") }
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
                Box(modifier = Modifier.fillMaxSize()) { Text("экран настроек") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinFlowTheme {
        ExpenseScreen()
    }
}