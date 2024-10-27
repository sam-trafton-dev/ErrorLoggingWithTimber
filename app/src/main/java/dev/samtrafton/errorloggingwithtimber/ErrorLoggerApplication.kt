package dev.samtrafton.errorloggingwithtimber

import android.app.Application
import android.content.Context
import android.os.Debug
import timber.log.Timber

class ErrorLoggerApplication : Application() {
    override fun onCreate() {
        // Per Timber docs the best time to create a debug tree is at initialization of the application
        super.onCreate()
        if (BuildConfig.DEBUG) {  // <-- This is more commonly used
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }

        Timber.d("Timber is initialized")
    }

    companion object : Application() {
        fun getContext(): Context {
            return applicationContext
        }
    }
}

class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == android.util.Log.ERROR) {
            // If this was connected to Crashalytics we could add this to the report
            t?.let {
                // Log the error to a reporting service
            }
        }
    }
}