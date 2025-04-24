package com.example.myclock

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.util.Log
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */class AnalogClockWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            Log.d("MyClockWidget", "onUpdate() called")

            AnalogClockWidgetService.updateClockWidget(context, appWidgetManager, appWidgetId)
            AnalogClockWidgetService.scheduleWidgetUpdate(context) // Ensure periodic updates
        }
    }
}
