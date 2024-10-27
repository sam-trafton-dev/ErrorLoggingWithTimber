package dev.samtrafton.errorloggingwithtimber.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.samtrafton.errorloggingwithtimber.ErrorLoggerViewModel
import dev.samtrafton.errorloggingwithtimber.ErrorType

@Composable
fun ErrorLoggerScreen(
    padding: PaddingValues,
    viewModel: ErrorLoggerViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Error Logger Demo",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp, top = 24.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ErrorButton(
                text = "Null Pointer",
                onClick = { viewModel.generateError(ErrorType.NULL_POINTER) }
            )
            ErrorButton(
                text = "Arithmetic",
                onClick = { viewModel.generateError(ErrorType.ARITHMETIC) }
            )
            ErrorButton(
                text = "Index OOB",
                onClick = { viewModel.generateError(ErrorType.INDEX_OUT_OF_BOUNDS) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Error Logs:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                // Display each generated error that is added to the logs
                viewModel.logs.value.forEach { log ->
                    Text(
                        text = log,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        Button(onClick = {
            println(viewModel.saveLogs())
            viewModel.clearLogs()

        }, ) {
            Text("Save Log")
        }
    }
}

@Composable
fun ErrorButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(text)
    }
}