package com.example.myclock

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log

class AnalogClockWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            Log.d("AnalogClockWidget", "Widget update triggered")
            AnalogClockWidgetService.updateClockWidget(context, appWidgetManager, appWidgetId)
        }
        // Ensure periodic updates only when the first widget is added
        AnalogClockWidgetService.scheduleWidgetUpdate(context)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            Log.d("AnalogClockWidget", "Received update broadcast")
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val componentName = context.packageName + ".AnalogClockWidgetProvider"
            val appWidgetIds = appWidgetManager.getAppWidgetIds(android.content.ComponentName(context, this::class.java))

            for (appWidgetId in appWidgetIds) {
                AnalogClockWidgetService.updateClockWidget(context, appWidgetManager, appWidgetId)
            }
        }
    }
}
