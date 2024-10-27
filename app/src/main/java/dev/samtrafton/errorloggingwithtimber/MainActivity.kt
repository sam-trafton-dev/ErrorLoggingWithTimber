package dev.samtrafton.errorloggingwithtimber

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.samtrafton.errorloggingwithtimber.ui.screen.ErrorLoggerScreen
import dev.samtrafton.errorloggingwithtimber.ui.theme.ErrorLoggingWithTimberTheme

class ErrorLoggerViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ErrorLoggerViewModel::class.java)) {
            return ErrorLoggerViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ErrorLoggingWithTimberTheme {
                val viewModel = ErrorLoggerViewModel(application = application)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ErrorLoggerScreen(viewModel = viewModel, padding = innerPadding)
                }
            }
        }
    }
}

