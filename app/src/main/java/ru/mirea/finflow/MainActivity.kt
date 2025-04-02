package ru.mirea.finflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.mirea.expense.presentation.ExpenseScreen
import ru.mirea.uikit.theme.FinFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinFlowTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { paddings ->
                    Column(
                        modifier = Modifier.padding(paddings)
                    ) {
                        ExpenseScreen()
                    }
                }
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