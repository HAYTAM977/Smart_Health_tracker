package com.example.myclock

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class WidgetUpdateWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val appWidgetManager = AppWidgetManager.getInstance(applicationContext)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(applicationContext, AnalogClockWidgetProvider::class.java)
        )
        for (appWidgetId in appWidgetIds) {
            Log.d("WidgetUpdateWorker", "Updating widget with ID: $appWidgetId")
            AnalogClockWidgetService.updateClockWidget(
                applicationContext,
                appWidgetManager,
                appWidgetId
            )
        }
        return Result.success()
    }
}

