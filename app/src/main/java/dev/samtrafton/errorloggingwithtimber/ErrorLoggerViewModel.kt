package dev.samtrafton.errorloggingwithtimber

import android.app.Application
import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import timber.log.Timber
import java.io.BufferedWriter
import java.io.File
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ErrorLoggerViewModel(application: Application) : androidx.lifecycle.ViewModel() {
    private val _logs = mutableStateOf<List<String>>(emptyList())
    val logs: State<List<String>> = _logs
    private val context = application.applicationContext

    fun generateError(type: ErrorType) {
        try {
            when (type) {
                ErrorType.NULL_POINTER -> {
                    val str: String? = null
                    str!!.length
                }
                ErrorType.ARITHMETIC -> {
                    val result = 1 / 0
                }
                ErrorType.INDEX_OUT_OF_BOUNDS -> {
                    val list = listOf(1, 2, 3)
                    list[5]
                }
            }
        } catch (e: Exception) {
            val logMessage = "${System.currentTimeMillis()}: ${e.javaClass.simpleName} - ${e.message}"
            _logs.value = _logs.value + logMessage
            Timber.e(e, "Error generated: ${e.message}")
        }
    }
    // Clear error list
    fun clearLogs() {
        _logs.value = emptyList()
        Timber.i("Logsc cleared")
    }

    fun saveLogs(): Result<String> {

        return try {
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
            val filename = "error_logs_$timestamp.txt"

             context.openFileOutput(filename, Context.MODE_PRIVATE).use { fos ->
                val writer = BufferedWriter(OutputStreamWriter(fos))
                logs.value.forEach { log ->
                    writer.write(log)
                    writer.newLine()
                }
                writer.flush()
            }
            val file = File(context.filesDir, filename)
            Timber.i("Logs saved to: ${file.absolutePath}")
            Result.success(file.absolutePath)
        } catch (e: Exception) {
            Timber.e(e, "Failed to saved logs")
            Result.failure(e)
        }
    }
}

enum class ErrorType {
    NULL_POINTER,
    ARITHMETIC,
    INDEX_OUT_OF_BOUNDS
}