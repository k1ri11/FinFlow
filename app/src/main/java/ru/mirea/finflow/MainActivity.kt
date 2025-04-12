package ru.mirea.finflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.auth.presentation.login.LoginScreen
import ru.mirea.expense.presentation.ExpenseScreen
import ru.mirea.uikit.theme.FinFlowTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinFlowTheme {
                LoginScreen(
                    onNavigateToRegister = {},
                    onNavigateToMain = {},
                )
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